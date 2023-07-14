package com.example.estore.ui.login

import androidx.lifecycle.ViewModel
import com.example.estore.common.Result
import com.example.estore.data.model.User
import com.example.estore.repositories.UserRepository
import com.example.estore.storage.UserStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val repository: UserRepository,
    private val userStorage: UserStorage
) : ViewModel() {

    suspend fun login(user: User) = flow {
        val login = repository.login(user)
        when (login) {
            is Result.Success -> {
                userStorage.setActiveToken(login.data?.token.orEmpty())
            }
            else -> {}
        }
        emit(login)
    }.flowOn(Dispatchers.IO)
}