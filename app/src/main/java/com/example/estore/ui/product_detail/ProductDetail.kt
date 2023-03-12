package com.example.estore.ui.product_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.estore.R
import com.example.estore.data.Resource
import com.example.estore.data.model.Product
import com.example.estore.databinding.FragmentProductDetailBinding
import com.example.estore.ui.home.HomeVM
import com.example.estore.utils.ImageUtil
import kotlinx.android.synthetic.main.layout_top_panel.*
import kotlinx.android.synthetic.main.layout_top_panel.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductDetail : Fragment() {
    var binding: FragmentProductDetailBinding? = null

    private val safeArgs: ProductDetailArgs by navArgs()
    private val homeVM: HomeVM by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = safeArgs.product
        binding?.let { binding ->
            binding.apply {
                setUpTopPanel(product)
                toggleProductFavStatus(product, isBtnToggled = false)
                ImageUtil.loadImageInto(product.image, productImage)
                productTitle.text = product.title
                productDesc.text = product.description
                price.text = "$ ${product.price}"
            }
        }
    }

    private fun toggleProductFavStatus(product: Product, isBtnToggled: Boolean) {
        lifecycleScope.launch {
            homeVM.getAllFavoriteProducts().collect { res ->
                when (res) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val products = res.data
                        //Check the Status on First time
                        if (!products.isNullOrEmpty() && !isBtnToggled) {
                            updateFavoriteState(products.contains(product))
                        } else {
                            updateFavoriteState(false)
                        }
                        // check if the button is toggled
                        if (isBtnToggled) {
                            if (!products.isNullOrEmpty() && products.contains(product)) {
                                // remove the product
                                homeVM.deleteFavoriteProduct(product)
                                binding?.topPanel?.actionIcon?.apply {
                                    isEnabled = false
                                    alpha = 0.5f
                                }
                                delay(300L)
                                updateFavoriteState(false)
                            }

                            if (products != null && !products.contains(product)) {
                                // Add Product
                                homeVM.addFavoriteProduct(product)
                                binding?.topPanel?.actionIcon?.apply {
                                    isEnabled = false
                                    alpha = 0.5f
                                }
                                delay(300L)
                                updateFavoriteState(true)
                            }
                        }

                    }
                    is Resource.Error -> {}
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpTopPanel(product: Product) {
        binding?.topPanel?.apply {
            screenTitle.text = "Product Detail"
            actionIcon.apply {
                layoutParams.width = 100
                layoutParams.height = 100
                setImageResource(R.drawable.ic_favorite_selector)
                setOnClickListener {
                    toggleProductFavStatus(product, isBtnToggled = true)
                }
            }
            backIcon.apply {
                setOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun updateFavoriteState(isFav: Boolean) {
        binding?.topPanel?.actionIcon?.apply {
            isSelected = isFav
            isEnabled = true
            alpha = 1f
        }
    }
}