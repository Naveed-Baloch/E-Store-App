package com.example.estore.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.estore.data.model.Product
import com.example.estore.databinding.LayoutProductItemHomeBinding
import com.example.estore.ui.common.ProductDiffCallback
import com.example.estore.utils.ImageUtil

class ProductAdapter(private var products: List<Product>, val onProductClicked: (Product) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ProductVH>() {

    class ProductVH(private val binding: LayoutProductItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product, onProductClicked: (Product) -> Unit) {
            binding.productTitle.text = product.title
            binding.productDesc.text = product.description
            binding.productPrice.text = "$ ${product.price}"
            ImageUtil.loadImageInto(product.image, binding.productImageView)
            itemView.setOnClickListener {
                onProductClicked(product)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProductVH {
        val binding =
            LayoutProductItemHomeBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ProductVH(binding)
    }

    override fun onBindViewHolder(viewHolder: ProductVH, position: Int) {
        viewHolder.bind(products[position], onProductClicked)
    }

    override fun getItemCount(): Int {
        return products.size
    }
    fun setData(newProducts: List<Product>) {
        val diffCallback = ProductDiffCallback(products, newProducts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        products = emptyList()
        products = newProducts
        diffResult.dispatchUpdatesTo(this)
    }
}