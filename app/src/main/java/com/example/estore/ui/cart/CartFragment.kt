package com.example.estore.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estore.databinding.FragmentCartBinding
import com.example.estore.ui.cart.adapter.CartAdapter

class CartFragment : Fragment() {
    private var binding: FragmentCartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = CartAdapter()
        binding?.let {
            it.cartProductsRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                this.adapter = adapter
            }
            it.totalPrice.text = "$ 245"
            setUpTopPanel(it)
        }
    }

    private fun setUpTopPanel(binding: FragmentCartBinding) {
        binding.topPanel.apply {
            screenTitle.text = "Cart"
            actionIcon.setOnClickListener {

            }
            backIcon.isVisible = false

        }
    }
}