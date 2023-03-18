package com.example.estore.extensions

import com.example.estore.data.model.Product

fun List<Product>.containsProduct(productId: Int): Boolean {
    return this.any { it.id == productId }
}