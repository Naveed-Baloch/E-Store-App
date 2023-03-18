package com.example.estore.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Cart")
@TypeConverters(ProductListConverter::class)
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val products: List<Product> = emptyList()
) : Parcelable