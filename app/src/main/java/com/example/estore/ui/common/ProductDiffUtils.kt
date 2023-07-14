package com.example.estore.ui.common

import androidx.recyclerview.widget.DiffUtil
import com.example.estore.data.model.Product

class ProductDiffCallback(
    private val oldProducts: List<Product>,
    private val newProducts: List<Product>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldProducts.size
    }

    override fun getNewListSize(): Int {
        return newProducts.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct = oldProducts[oldItemPosition]
        val newProduct = newProducts[newItemPosition]
        return oldProduct.id == newProduct.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct = oldProducts[oldItemPosition]
        val newProduct = newProducts[newItemPosition]
        return oldProduct == newProduct
    }
}