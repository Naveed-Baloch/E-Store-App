package com.example.estore.common

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.estore.R

@SuppressLint("PrivateResource")
fun alert(
    context: Context,
    title: Int? = null,
    message: Int = 0,
    dismissWithOk: Boolean = true,
    onDismissAction: () -> Unit = {}
): AlertDialog.Builder {

    val builder = AlertDialog.Builder(context)
    title?.let {
        builder.setTitle(it)
    }
    if (message != 0) {
        builder.setMessage(message)
    }
    if (dismissWithOk) {
        builder.setPositiveButton("ok") { _, _ ->
            onDismissAction()
        }
        builder.setOnDismissListener {
            onDismissAction()
        }
    }
    return builder
}

