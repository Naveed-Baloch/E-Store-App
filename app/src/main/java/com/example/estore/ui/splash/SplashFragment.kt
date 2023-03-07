package com.example.estore.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.estore.databinding.FragmentSplashBinding
import com.example.estore.storage.UserStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {
    var binding: FragmentSplashBinding? = null

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
                getStarted.isVisible = false
                if (!userStorage.isFirstTime) {
                    lifecycleScope.launch {
                        navigateToHome()
                    }
                } else {
                    getStarted.isVisible = true
                    getStarted.setOnClickListener {
                        userStorage.isFirstTime = false
                        var action =
                            SplashScreenDirections.actionSplashScreen2ToLoginFragment2()
                        if (!userStorage.getActiveToken().isNullOrEmpty()) {
                            action = SplashScreenDirections.actionSplashScreenToHome2()
                        }
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    private suspend fun navigateToHome() {
        delay(2000L)
        val action = SplashScreenDirections.actionSplashScreenToHome2()
        findNavController().navigate(action)
    }
}