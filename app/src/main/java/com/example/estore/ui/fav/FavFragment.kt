package com.example.estore.ui.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.estore.databinding.FragmentFavBinding
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
        val adapter = ProductAdapter()
        binding?.let {
            it.favProducts.layoutManager = GridLayoutManager(requireContext(), 2)
            it.favProducts.adapter = adapter
        }
    }
}