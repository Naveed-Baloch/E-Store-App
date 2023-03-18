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
import com.example.estore.data.model.Cart
import com.example.estore.data.model.Product
import com.example.estore.databinding.FragmentProductDetailBinding
import com.example.estore.extensions.containsProduct
import com.example.estore.ui.cart.CartVM
import com.example.estore.ui.home.HomeVM
import com.example.estore.utils.ImageUtil
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_top_panel.*
import kotlinx.android.synthetic.main.layout_top_panel.view.*
import kotlinx.coroutines.*

class ProductDetail : Fragment() {
    var binding: FragmentProductDetailBinding? = null

    private val safeArgs: ProductDetailArgs by navArgs()
    private val homeVM: HomeVM by activityViewModels()
    private val cartVM: CartVM by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
                updateAddToCartBtnTxt(product)
                addToCart.setOnClickListener {
                    addCartProduct(product)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateAddToCartBtnTxt(product: Product) {
        lifecycleScope.launchWhenStarted {
            val job = CoroutineScope(Dispatchers.IO).async {
                return@async cartVM.getCart()
            }
            val cart = job.await()
            val products = cart?.products ?: emptyList()
            if (products.containsProduct(product.id)) {
                binding!!.addToCartBtnTxt.text = "Remove From Basket"
            } else {
                binding!!.addToCartBtnTxt.text = "Add To Basket"
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
                        // check if the button is toggled
                        if (isBtnToggled) {
                            if (!products.isNullOrEmpty() && products.containsProduct(product.id)) {
                                // remove the product
                                homeVM.deleteFavoriteProduct(product)
                                binding?.topPanel?.actionIcon?.apply {
                                    isEnabled = false
                                    alpha = 0.5f
                                }
                                delay(300L)
                                updateFavoriteState(false)
                            } else {
                                // Add Product
                                homeVM.addFavoriteProduct(product)
                                binding?.topPanel?.actionIcon?.apply {
                                    isEnabled = false
                                    alpha = 0.5f
                                }
                                delay(300L)
                                updateFavoriteState(true)
                            }
                        } else {
                            if (!products.isNullOrEmpty()) {
                                updateFavoriteState(products.containsProduct(product.id))
                            } else {
                                updateFavoriteState(false)
                            }
                        }
                    }
                    is Resource.Error -> {}
                }
            }
        }
    }

    private fun addCartProduct(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            val cart = cartVM.getCart()
            //first product
            if (cart == null) {
                product.quantity = 1
                val newCart = Cart(1, listOf(product))
                cartVM.insertCart(newCart)
            }
            val cartProducts = cart?.products ?: emptyList()
            if (cartProducts.containsProduct(product.id)) {
                val cartProductsUpdated = cartProducts.filter { it.id != product.id }
                cartVM.insertCart(Cart(1, cartProductsUpdated))
            } else {
                product.quantity = 1
                val cartProductsUpdated = cartProducts.plus(product)
                cartVM.insertCart(Cart(1, cartProductsUpdated))
            }
            updateAddToCartBtnTxt(product)
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