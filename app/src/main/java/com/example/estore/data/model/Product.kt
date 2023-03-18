package com.example.estore.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "product")
class Product(
    @PrimaryKey
    val id: Int,
    val category: String,
    val description: String,
    var quantity: Int?,
    val image: String,
    val price: String,
    val title: String
) : Parcelable