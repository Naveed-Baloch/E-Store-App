package com.example.estore.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.estore.databinding.FragmentProfileBinding
import com.example.estore.storage.UserStorage
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    @Inject
    lateinit var userStorage: UserStorage
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userStorage = UserStorage(requireContext())
        binding?.let {
            setUpTopPanel(it)
            it.logout.setOnClickListener {
                userStorage.clearActiveToken()
                val action =
                    ProfileFragmentDirections.actionProfileToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun setUpTopPanel(binding: FragmentProfileBinding) {
        binding.topPanel.apply {
            screenTitle.text = "Profile"
            actionIcon.isVisible = false
            backIcon.isVisible = false
        }
    }
}