package com.example.estore.ui.fav

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.estore.R
import com.example.estore.common.alert

import com.example.estore.data.model.Product
import com.example.estore.databinding.FragmentFavBinding
import com.example.estore.ui.common.CommonProductAdapter
import com.example.estore.ui.home.HomeVM
import kotlinx.android.synthetic.main.fragment_splash.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavFragment : Fragment() {
    private lateinit var binding: FragmentFavBinding
    private val homeVM: HomeVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setUpTopPanel()
            fetchProducts()
        }
    }

    private fun fetchProducts() {
        binding.progressBar.isVisible = true
        lifecycleScope.launch(Dispatchers.IO) {
            val products = homeVM.getAllFavoriteProducts()
            withContext(Dispatchers.Main) {
                if (products.isEmpty()) {
                    showEmpty()
                } else {
                    showFavList(products)
                }
            }
        }
    }

    private fun showFavList(products: List<Product>) {
        binding.apply {
            this.progressBar.isVisible = false
            this.emptyFavView.isVisible = false
            this.favProducts.isVisible = true
            this.favProducts.apply {
                val adapter = CommonProductAdapter(products, onProductClicked = { product ->
                    val directionToDetailProductPage =
                        FavFragmentDirections.actionFavoriteToProductDetail(product)
                    findNavController().navigate(directionToDetailProductPage)
                })
                layoutManager = GridLayoutManager(requireContext(), 2)
                this.adapter = adapter
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showEmpty() {
        binding.apply {
            this.progressBar.isVisible = false
            this.favProducts.isVisible = false
            this.emptyFavView.isVisible = true
            this.emptyFav.apply {
                emptyScreenImage.setImageResource(R.drawable.empty_fav)
                emptyScreenTitle.text = "No favorites yet"
                emptyScreenDesc.text = "Hit the orange button down\n below to Create an order"
                emptyScreenBtnTxt.text = "Start Ordering!"
                emptyScreenBtn.setOnClickListener {
                    val directionToHomePage =
                        FavFragmentDirections.actionFavoriteToHome()
                    findNavController().navigate(directionToHomePage)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpTopPanel() {
        binding.topPanel.apply {
            screenTitle.text = "Favorite"
            actionIcon.isVisible = false
            backIcon.isVisible = false
        }
    }
}