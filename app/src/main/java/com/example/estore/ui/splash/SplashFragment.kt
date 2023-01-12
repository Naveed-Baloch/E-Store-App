package com.example.estore.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.estore.R
import com.example.estore.databinding.FragmentSplashBinding

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {
    var binding: FragmentSplashBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.getStarted?.setOnClickListener {
            it.findNavController().navigate(R.id.loginFragment)
        }
    }
}