package com.example.estore.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.estore.R
import com.example.estore.databinding.FragmentFavBinding
import com.example.estore.databinding.FragmentProfileBinding
import com.example.estore.ui.common.SpacingItemDecoration
import com.example.estore.ui.home.adapters.ProductAdapter

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            setUpTopPanel(it)

        }
    }

    private fun setUpTopPanel(binding: FragmentProfileBinding){
        binding.topPanel.apply {
            screenTitle.text="Profile"
            actionIcon.isVisible=false
            backIcon.isVisible=false

        }
    }
}