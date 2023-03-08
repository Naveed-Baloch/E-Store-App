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
import com.example.estore.data.Resource
import com.example.estore.databinding.FragmentHotSellingBinding
import com.example.estore.ui.home.adapters.ProductAdapter
import kotlinx.coroutines.launch

class HotSellingProductsFragment : Fragment() {
    private var binding: FragmentHotSellingBinding? = null
    private val homeVM: HomeVM by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotSellingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { binding ->
            binding.hotSellingProductsRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            lifecycleScope.launch {
                homeVM.fetchProducts().collect { res ->
                    when (res) {
                        is Resource.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        is Resource.Success -> {
                            binding.progressBar.isVisible = false
                            res.data?.data?.let {
                                binding.hotSellingProductsRv.isVisible = true
                                val adapter = ProductAdapter(it, onProductClicked = { product ->
                                    val directionToDetailProductPage =
                                        HomeFragmentDirections.actionHomeToProductDetail(product)
                                    findNavController().navigate(directionToDetailProductPage)
                                })
                                binding.hotSellingProductsRv.adapter = adapter
                            }
                        }
                        is Resource.Error -> {
                            binding.progressBar.isVisible = false
                            alert(requireContext())
                                .setTitle("Something went wrong :(")
                                .setMessage(res.cause?.message)
                                .show()
                        }
                    }
                }
            }
        }

    }
}