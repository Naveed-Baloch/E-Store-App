package com.example.estore.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.estore.R
import com.example.estore.databinding.FragmentHomeBinding
import com.example.estore.ui.home.adapters.ProductsViewPagerAdapter

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductsViewPagerAdapter(2, parentFragmentManager)
        binding?.let { binding ->
            binding.homeSearchView.setOnClickListener {
                it.findNavController().navigate(R.id.searchFragment)
            }
            binding.productsViewPager.adapter = adapter
            binding.homeTabs.setupWithViewPager(binding.productsViewPager)
        }
    }
}