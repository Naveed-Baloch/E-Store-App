package com.example.estore.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.estore.R
import com.example.estore.databinding.LayoutProductItemBinding


class ProductAdapter() :
    RecyclerView.Adapter<ProductAdapter.ProductVH>() {

    class ProductVH(private val binding: LayoutProductItemBinding) :
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
            LayoutProductItemBinding.inflate(
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