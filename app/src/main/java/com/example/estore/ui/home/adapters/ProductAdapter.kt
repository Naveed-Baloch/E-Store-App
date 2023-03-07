package com.example.estore.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.estore.data.model.Product
import com.example.estore.databinding.LayoutProductItemHomeBinding
import com.example.estore.utils.ImageUtil

class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductVH>() {

    class ProductVH(private val binding: LayoutProductItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            binding.productTitle.text = product.title
            binding.productDesc.text = product.description
            binding.productPrice.text = "$ ${product.price}"
            ImageUtil.loadImageInto(product.image, binding.productImageView)
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
        viewHolder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}