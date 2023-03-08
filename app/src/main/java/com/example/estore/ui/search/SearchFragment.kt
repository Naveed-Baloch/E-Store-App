package com.example.estore.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.estore.common.alert
import com.example.estore.data.Resource
import com.example.estore.databinding.FragmentSearchBinding
import com.example.estore.ui.common.CommonProductAdapter
import com.example.estore.ui.home.HomeVM
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private val homeVM: HomeVM by activityViewModels()
    private val searchVM: SearchVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { binding ->
            binding.searchProductsRv.layoutManager = GridLayoutManager(requireContext(), 2)

            binding.backIcon.setOnClickListener {
                findNavController().popBackStack()
            }
            lifecycleScope.launch {
                searchVM.searchResults.collect { res ->
                    when (res) {
                        is Resource.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.searchProductsRv.isVisible = false
                        }
                        is Resource.Success -> {
                            binding.progressBar.isVisible = false
                            res.data?.data?.let {
                                binding.searchProductsRv.isVisible = true
                                val adapter =
                                    CommonProductAdapter(it, onProductClicked = { product ->
                                        val directionToDetailProductPage =
                                            SearchFragmentDirections.actionSearchFragmentToProductDetail(
                                                product
                                            )
                                        findNavController().navigate(directionToDetailProductPage)
                                    })
                                binding.searchProductsRv.adapter = adapter
                            }
                        }
                        is Resource.Error -> {
                            binding.searchProductsRv.isVisible = false
                            binding.progressBar.isVisible = false
                            alert(requireContext()).setTitle("Something went wrong :(")
                                .setMessage(res.cause?.message).show()
                        }
                    }
                }
            }
            // fetch products for the preload
            lifecycleScope.launch {
                homeVM.fetchProducts().collect { res ->
                    when (res) {
                        is Resource.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.searchProductsRv.isVisible = false
                        }
                        is Resource.Success -> {
                            binding.progressBar.isVisible = false
                            res.data?.data?.let {
                                binding.searchProductsRv.isVisible = true
                                val adapter =
                                    CommonProductAdapter(it, onProductClicked = { product ->
                                        val directionToDetailProductPage =
                                            SearchFragmentDirections.actionSearchFragmentToProductDetail(
                                                product
                                            )
                                        findNavController().navigate(directionToDetailProductPage)
                                    })
                                binding.searchProductsRv.adapter = adapter
                            }
                        }
                        is Resource.Error -> {
                            binding.progressBar.isVisible = false
                            alert(requireContext()).setTitle("Something went wrong :(")
                                .setMessage(res.cause?.message).show()
                        }
                    }
                }
            }
            binding.homeSearchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        searchVM.searchQuery.value = it
                        return true
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        searchVM.searchQuery.value = it
                    }
                    return true
                }
            })
        }
    }
}