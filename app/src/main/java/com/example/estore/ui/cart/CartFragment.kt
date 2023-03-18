package com.example.estore.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estore.data.model.Cart
import com.example.estore.databinding.FragmentCartBinding
import com.example.estore.ui.cart.adapter.CartAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CartFragment : Fragment() {
    private var binding: FragmentCartBinding? = null
    private val cartVM: CartVM by activityViewModels()
    private var cart: Cart? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            loadCartData()
            val adapter = CartAdapter(cart?.products ?: emptyList())
            binding?.let {
                it.cartProductsRv.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    this.adapter = adapter
                }
                it.price.text = "$ 245"
                setUpTopPanel(it)
            }
        }
    }

    private suspend fun loadCartData() {
        val job = CoroutineScope(Dispatchers.IO).async {
            cartVM.getCart()
        }
        cart = job.await()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpTopPanel(binding: FragmentCartBinding) {
        binding.topPanel.apply {
            screenTitle.text = "Cart"
            actionIcon.setOnClickListener {

            }
            backIcon.isVisible = false

        }
    }
}