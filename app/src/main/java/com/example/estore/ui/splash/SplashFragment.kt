package com.example.estore.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.estore.databinding.FragmentSplashBinding
import com.example.estore.storage.UserStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {
    var binding: FragmentSplashBinding? = null
    private val directionToLoginFragment =
        SplashScreenDirections.actionSplashScreen2ToLoginFragment2()
    private val directionToHomeFragment =
        SplashScreenDirections.actionSplashScreenToHome2()

    @Inject
    lateinit var userStorage: UserStorage
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userStorage = UserStorage(requireContext())

        binding?.let { binding ->
            binding.apply {
                if (!userStorage.isFirstTime) {
                    getStarted.visibility = View.INVISIBLE
                    text.visibility = View.INVISIBLE
                }
                if (userStorage.getActiveToken() != null) {
                    navigate(directionToHomeFragment)
                } else {
                    navigate(directionToLoginFragment)
                }
                getStarted.setOnClickListener {
                    userStorage.isFirstTime = false
                    findNavController().navigate(directionToLoginFragment)
                }
            }
        }
    }

    private fun navigate(direction: NavDirections) {
        lifecycleScope.launch {
            delay(2000L)
            val action =
                findNavController().navigate(direction)
        }
    }
}