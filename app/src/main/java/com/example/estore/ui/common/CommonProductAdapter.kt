package com.example.estore.ui.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.estore.data.model.Product
import com.example.estore.databinding.LayoutProductItemBinding
import com.example.estore.utils.ImageUtil


class CommonProductAdapter(
    private val products: List<Product>,
    private val onProductClicked: (Product) -> Unit
) :
    RecyclerView.Adapter<CommonProductAdapter.ProductVH>() {

    class ProductVH(private val binding: LayoutProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product, onProductClicked: (Product) -> Unit) {
            binding.productTitle.text = product.title
            binding.productPrice.text = "$ ${product.price}"
            ImageUtil.loadImageInto(product.image, binding.productImageView)
            itemView.setOnClickListener {
                onProductClicked(product)
            }
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
        viewHolder.bind(products[position], onProductClicked)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}