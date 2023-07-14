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
import kotlinx.android.synthetic.main.fragment_home.homeSearchView
import kotlinx.android.synthetic.main.fragment_home.homeTabs
import kotlinx.android.synthetic.main.fragment_home.productsViewPager

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductsViewPagerAdapter(2, childFragmentManager)
        homeSearchView.setOnClickListener { it.findNavController().navigate(R.id.searchFragment) }
        productsViewPager.adapter = adapter
        homeTabs.setupWithViewPager(binding.productsViewPager)
    }
}