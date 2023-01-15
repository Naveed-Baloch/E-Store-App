package com.example.estore.ui.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.estore.R
import com.example.estore.databinding.FragmentFavBinding
import com.example.estore.ui.common.SpacingItemDecoration
import com.example.estore.ui.home.adapters.ProductAdapter

class FavFragment : Fragment() {
    private var binding: FragmentFavBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = ProductAdapter()
        binding?.let {
            it.favProducts.apply {
                addItemDecoration(
                    SpacingItemDecoration(
                        left = resources.getDimensionPixelSize(R.dimen.margin),
                        top = resources.getDimensionPixelSize(R.dimen.margin),
                        right = resources.getDimensionPixelSize(R.dimen.margin),
                    )
                )
                layoutManager = GridLayoutManager(requireContext(), 2)
                this.adapter = adapter
            }
            setUpTopPanel(it)
        }
    }

    private fun setUpTopPanel(binding: FragmentFavBinding) {
        binding.topPanel.apply {
            screenTitle.text = "Favorite"
            actionIcon.isVisible = false
            backIcon.isVisible = false
        }
    }
}