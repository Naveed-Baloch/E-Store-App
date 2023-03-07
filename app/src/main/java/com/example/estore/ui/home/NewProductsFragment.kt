package com.example.estore.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estore.common.alert
import com.example.estore.data.Resource
import com.example.estore.databinding.FragmentNewProductsBinding
import com.example.estore.ui.home.adapters.ProductAdapter
import kotlinx.coroutines.launch

class NewProductsFragment : Fragment() {
    private val homeVM: HomeVM by activityViewModels()
    private var binding: FragmentNewProductsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewProductsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { binding ->
            binding.newSellingProductsRv.layoutManager =
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
                                binding.newSellingProductsRv.isVisible = true
                                val adapter = ProductAdapter(it)
                                binding.newSellingProductsRv.adapter = adapter
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