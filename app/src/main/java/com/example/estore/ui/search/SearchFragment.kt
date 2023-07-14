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
import com.example.estore.data.Result
import com.example.estore.data.model.Product
import com.example.estore.databinding.FragmentSearchBinding
import com.example.estore.ui.common.CommonProductAdapter
import com.example.estore.ui.home.HomeVM
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val homeVM: HomeVM by activityViewModels()
    private val searchVM: SearchVM by activityViewModels()
    private lateinit var adapter: CommonProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setOnOnClickListeners()
        searchProducts()
        fetchPreLoadProducts()
        setupQueryTextListener()
    }

    private fun setUpAdapter() {
        binding.searchProductsRv.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = CommonProductAdapter(emptyList(), onProductClicked = this::onProductClicked)
        binding.searchProductsRv.adapter = adapter
    }

    private fun setOnOnClickListeners() {
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupQueryTextListener() {
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

    private fun onProductClicked(product: Product) {
        val directionToDetailProductPage = SearchFragmentDirections.actionSearchFragmentToProductDetail(product)
        findNavController().navigate(directionToDetailProductPage)
    }

    private fun fetchPreLoadProducts() {
        if (searchVM.filteredProducts.value.isEmpty()) {
            lifecycleScope.launch {
                searchVM.fetchProducts().collect { res ->
                    binding.progressBar.isVisible = res is Result.Loading
                    if (res is Result.Error) {
                        binding.progressBar.isVisible = false
                        alert(requireContext()).setTitle("Something went wrong :(").setMessage(res.cause?.message).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            searchVM.filteredProducts.collect { products ->
                binding.searchProductsRv.isVisible = true
                adapter.setData(products)
            }
        }

    }

    private fun searchProducts() {
        lifecycleScope.launch {
            searchVM.searchResults.collect { res ->
                when (res) {
                    is Result.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.searchProductsRv.isVisible = false
                    }

                    is Result.Success -> {
                        binding.progressBar.isVisible = false
                        binding.searchProductsRv.isVisible = true
                    }

                    is Result.Error -> {
                        binding.searchProductsRv.isVisible = false
                        binding.progressBar.isVisible = false
                        alert(requireContext()).setTitle("Something went wrong :(")
                            .setMessage(res.cause?.message).show()
                    }
                }
            }
        }
    }
}