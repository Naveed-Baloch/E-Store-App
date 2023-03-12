package com.example.estore.ui.fav

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.estore.R
import com.example.estore.common.alert
import com.example.estore.data.Resource
import com.example.estore.data.model.Product
import com.example.estore.databinding.FragmentFavBinding
import com.example.estore.ui.common.CommonProductAdapter
import com.example.estore.ui.home.HomeVM
import kotlinx.android.synthetic.main.fragment_splash.view.*
import kotlinx.coroutines.launch

class FavFragment : Fragment() {
    private var binding: FragmentFavBinding? = null
    private val homeVM: HomeVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            setUpTopPanel()
            it.progressBar.isVisible = true
            lifecycleScope.launch {
                homeVM.getAllFavoriteProducts().collect { res ->
                    when (res) {
                        is Resource.Loading -> {
                            it.emptyFavView.isVisible = false
                            it.favProducts.isVisible = false
                        }
                        is Resource.Success -> {
                            it.progressBar.isVisible = false
                            val products = res.data
                            if (products.isNullOrEmpty()) {
                                showEmpty()
                            } else {
                                showFavList(products)
                            }
                        }
                        is Resource.Error -> {
                            it.progressBar.isVisible = false
                            it.emptyFavView.isVisible = false
                            it.favProducts.isVisible = false
                            alert(requireContext()).setTitle("Something went wrong :(")
                                .setMessage(res.cause?.message).show()
                        }
                    }
                }
            }
        }
    }

    private fun showFavList(products: List<Product>) {
        binding?.let {
            it.progressBar.isVisible = false
            it.emptyFavView.isVisible = false
            it.favProducts.isVisible = true
            it.favProducts.apply {
                val adapter = CommonProductAdapter(products, onProductClicked = { product ->
                    val directionToDetailProductPage =
                        FavFragmentDirections.actionFavoriteToProductDetail(product)
                    findNavController().navigate(directionToDetailProductPage)
                })
                layoutManager = GridLayoutManager(requireContext(), 2)
                this.adapter = adapter
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showEmpty() {
        binding?.let {
            it.favProducts.isVisible = false
            it.emptyFavView.isVisible = true
            it.emptyFav.apply {
                emptyScreenImage.setImageResource(R.drawable.empty_fav)
                emptyScreenTitle.text = "No favorites yet"
                emptyScreenDesc.text = "Hit the orange button down\n below to Create an order"
                emptyScreenBtnTxt.text = "Start Ordering!"
                emptyScreenBtn.setOnClickListener {
                    val directionToHomePage =
                        FavFragmentDirections.actionFavoriteToHome()
                    findNavController().navigate(directionToHomePage)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpTopPanel() {
        binding?.topPanel?.apply {
            screenTitle.text = "Favorite"
            actionIcon.isVisible = false
            backIcon.isVisible = false
        }
    }
}