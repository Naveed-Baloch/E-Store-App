package com.example.estore.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estore.common.alert
import com.example.estore.data.Result
import com.example.estore.data.model.Product
import com.example.estore.databinding.FragmentHotSellingBinding
import com.example.estore.ui.home.adapters.ProductAdapter
import kotlinx.coroutines.launch

class HotSellingProductsFragment : Fragment() {
    private lateinit var binding: FragmentHotSellingBinding
    private val homeVM: HomeVM by activityViewModels()
    private lateinit var adapter: ProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotSellingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductAdapter()
        attachObservers()
    }

    private fun setupProductAdapter() {
        adapter = ProductAdapter(emptyList(), this::onProductClicked)
        binding.hotSellingProductsRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.hotSellingProductsRv.adapter = adapter
    }

    private fun onProductClicked(product: Product) {
        val directionToDetailProductPage = HomeFragmentDirections.actionHomeToProductDetail(product)
        findNavController().navigate(directionToDetailProductPage)
    }

    private fun attachObservers() {
        lifecycleScope.launch {
            homeVM.products.collect { products ->
                if (products.isEmpty()) {
                    homeVM.fetchProducts().collect { res ->
                        binding.progressBar.isVisible = res is Result.Loading
                        if (res is Result.Error) {
                            alert(requireContext())
                                .setTitle("Something went wrong :(")
                                .setMessage(res.cause?.message)
                                .show()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            homeVM.products.collect { products ->
                binding.hotSellingProductsRv.isVisible = true
                adapter.setData(products)
            }
        }
    }
}