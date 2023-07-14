package com.example.estore.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.estore.common.Result
import com.example.estore.common.alert
import com.example.estore.data.model.Token
import com.example.estore.data.model.User
import com.example.estore.databinding.FragmentLoginBinding
import com.example.estore.utils.SimpleTextWatcher
import kotlinx.coroutines.launch

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginVM: LoginVM by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            loginBtn.isEnabled = false
            val textWatcher = object : SimpleTextWatcher() {
                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    emailInput.error = null
                    passwordInput.error = null
                    if (binding.emailInput.text.toString().isValidEmail()
                        && binding.passwordInput.text?.isNotEmpty() == true
                    ) {
                        loginBtn.isEnabled = true
                        loginBtn.alpha = 1f
                    } else {
                        loginBtn.isEnabled = false
                        loginBtn.alpha = 0.5f
                    }
                }
            }
            emailInput.addTextChangedListener(textWatcher)
            passwordInput.addTextChangedListener(textWatcher)
            loginBtn.setOnClickListener {
                authenticate(binding)
            }
        }

    }

    private fun authenticate(binding: FragmentLoginBinding) {
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()

        updateLoadState(true)
        lifecycleScope.launch {
            loginVM.login(user = User("mor_2314", "83r5^_"))
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            updateLoadState(true)
                            val data = result.data
                            if (data is Token) {
                                // Handle success
                                val action = LoginFragmentDirections.actionLoginFragment2ToHomeFragment()
                                findNavController().navigate(action)
                            } else {
                                // Unexpected data type
                                updateLoadState(false)
                                alert(requireContext())
                                    .setTitle("Something went wrong :(")
                                    .setMessage("Unexpected data type")
                                    .show()
                            }
                        }

                        else -> {
                            // Handle error
                            updateLoadState(false)
                            alert(requireContext())
                                .setTitle("Something went wrong :(")
                                .setMessage(result.error?.message)
                                .show()
                        }
                    }
                }
        }
    }

    private fun updateLoadState(isLoading: Boolean) {
        binding.apply {
            progressBar.isVisible = isLoading
            loginBtnText.isVisible = !isLoading
        }
    }

    fun String.isValidEmail() = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}