package com.example.estore.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.example.estore.data.model.User
import com.example.estore.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {
    var binding: FragmentLoginBinding? = null
    private val loginVM: LoginVM by activityViewModels()
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
        binding?.let { binding ->
            binding.loginBtn.setOnClickListener {
                authenticate(binding)
            }
        }
    }

    private fun authenticate(binding: FragmentLoginBinding) {
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()

        //Todo
        loginVM.viewModelScope.launch {
            val token = loginVM.login(user = User("mor_2314", "83r5^_"))
            Log.d(TAG, "authenticate: ${token.token}")
        }

    }

}