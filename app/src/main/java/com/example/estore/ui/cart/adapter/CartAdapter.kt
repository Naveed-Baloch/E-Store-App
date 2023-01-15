package com.example.estore.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.estore.R
import com.example.estore.databinding.LayoutCartItemBinding


class CartAdapter() : RecyclerView.Adapter<CartAdapter.ProductVH>() {

    class ProductVH(private val binding: LayoutCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                carProductTitle.text = "Apple Watch"
                cartProductPrice.text = "$ 24"
                cartProductQty.text = "2"
                cartProductImage.setImageResource(R.drawable.product_image)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProductVH {
        val binding = LayoutCartItemBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false
        )
        return ProductVH(binding)
    }

    override fun onBindViewHolder(viewHolder: ProductVH, position: Int) {
        viewHolder.bind()
    }

    override fun getItemCount(): Int {
        return 12
    }
}