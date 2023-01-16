package com.example.estore.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.estore.databinding.FragmentSearchBinding
import com.example.estore.ui.common.CommonProductAdapter

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CommonProductAdapter()
        binding?.let {
            it.searchProductsRv.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                this.adapter = adapter
            }
            it.backIcon.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}