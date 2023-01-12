package com.example.estore.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.estore.R
import com.example.estore.databinding.FragmentLoginBinding
import com.example.estore.databinding.FragmentSplashBinding

class LoginFragment : Fragment() {
    var binding: FragmentLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.loginBtn?.setOnClickListener {
            it.findNavController().navigate(R.id.homeFragment)
        }
    }

}