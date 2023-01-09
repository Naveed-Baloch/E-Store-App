package com.example.estore.ui.home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.estore.ui.home.HotSellingProductsFragment
import com.example.estore.ui.home.NewProductsFragment

class ProductsViewPagerAdapter(private val NUM_PAGES: Int, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int = NUM_PAGES

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "New Arrival"
            1 -> "Hot Selling"
            else -> "Default Page Name"
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> NewProductsFragment()
            1 -> HotSellingProductsFragment()
            else -> Fragment()
        }
    }

}