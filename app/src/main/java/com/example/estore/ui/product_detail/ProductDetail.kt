package com.example.estore.ui.product_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.estore.R
import com.example.estore.databinding.FragmentProductDetailBinding
import com.example.estore.databinding.LayoutTopPanelBinding
import com.example.estore.utils.ImageUtil
import kotlinx.android.synthetic.main.layout_top_panel.view.*

class ProductDetail : Fragment() {
    var binding: FragmentProductDetailBinding? = null

    private val safeArgs: ProductDetailArgs by navArgs()

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
                setUpTopPanel(this)
                ImageUtil.loadImageInto(product.image, productImage)
                productTitle.text = product.title
                productDesc.text = product.description
                price.text = "$ ${product.price}"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpTopPanel(binding: FragmentProductDetailBinding) {
        binding.topPanel.apply {
            screenTitle.text = "Product Detail"
            actionIcon.apply {
                isVisible = true
                layoutParams.width = 100
                layoutParams.height = 100
                setImageResource(R.drawable.ic_favorite_selector)
                setOnClickListener {
                    updateFavoriteState(true, binding.topPanel)
                }
            }
            backIcon.apply {
                isVisible = true
                setOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun updateFavoriteState(isFavorite: Boolean, binding: LayoutTopPanelBinding) {
        binding.topPanel.actionIcon.apply {
            isSelected = isFavorite
            isEnabled = true
            alpha = 1f
        }
    }
}