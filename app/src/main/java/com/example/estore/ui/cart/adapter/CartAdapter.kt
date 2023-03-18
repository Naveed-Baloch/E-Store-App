package com.example.estore.ui.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.estore.data.model.Product
import com.example.estore.databinding.LayoutCartItemBinding
import com.example.estore.utils.ImageUtil


class CartAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<CartAdapter.ProductVH>() {

    class ProductVH(private val binding: LayoutCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            binding.apply {
                carProductTitle.text = product.title
                cartProductPrice.text = "$ ${product.price}"
                cartProductQty.text = "${product.quantity}"
                ImageUtil.loadImageInto(product.image, binding.cartProductImage)
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
        viewHolder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}