package com.example.estore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.estore.databinding.ActivityMainBinding
import com.example.estore.databinding.ActivityUserBinding
import com.example.estore.storage.UserStorage
import com.example.estore.ui.login.LoginVM
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityUserBinding
    private val userActivityVM: UserActivityVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.userNavHostFragment.id) as NavHostFragment
        userActivityVM.showLoginFragment = intent.getBooleanExtra("showLoginFragment", false)
        navController = navHostFragment.navController
    }
}
@HiltViewModel
class UserActivityVM @Inject constructor() : ViewModel() {
    var showLoginFragment: Boolean = false
}
