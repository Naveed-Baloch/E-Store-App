package com.example.estore.ui.login

import androidx.lifecycle.ViewModel
import com.example.estore.data.model.User
import com.example.estore.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(private val repository: UserRepository) : ViewModel() {

    suspend fun login(user: User) = repository.login(user)

}