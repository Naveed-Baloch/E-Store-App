package com.example.estore.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.estore.R
import com.example.estore.databinding.LayoutProductItemBinding
import com.example.estore.databinding.LayoutProductItemHomeBinding


class ProductAdapter() :
    RecyclerView.Adapter<ProductAdapter.ProductVH>() {

    class ProductVH(private val binding: LayoutProductItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.productTitle.text = "Apple Watch"
            binding.productDesc.text = "New Brand"
            binding.productPrice.text = "$ 24"
            binding.productImageView.setImageResource(R.drawable.product_image)
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
        viewHolder.bind()
    }

    override fun getItemCount(): Int {
        return 10
    }
}