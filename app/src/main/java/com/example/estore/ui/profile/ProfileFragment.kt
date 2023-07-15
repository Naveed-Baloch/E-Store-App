package com.example.estore.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.estore.MainActivity
import com.example.estore.UserActivity
import com.example.estore.databinding.FragmentProfileBinding
import com.example.estore.storage.UserStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.logout
import kotlinx.android.synthetic.main.layout_top_panel.actionIcon
import kotlinx.android.synthetic.main.layout_top_panel.backIcon
import kotlinx.android.synthetic.main.layout_top_panel.screenTitle
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var userStorage: UserStorage
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpTopPanel()
        setUpListeners()
    }

    private fun setUpListeners() {
        logout.setOnClickListener {
            userStorage.clearActiveToken()
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra("showLoginFragment", true) // Set the value to true or false as needed
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setUpTopPanel() {
        screenTitle.text = "Profile"
        actionIcon.isVisible = false
        backIcon.isVisible = false
    }
}