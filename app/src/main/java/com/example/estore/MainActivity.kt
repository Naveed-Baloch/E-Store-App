package com.example.estore

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.estore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setOnItemSelectedListener { item -> handleMenuClick(item.itemId) }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splashScreen || destination.id == R.id.loginFragment || destination.id == R.id.searchFragment) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    private fun handleMenuClick(id: Int): Boolean {
        if (id == binding.bottomNav.selectedItemId) return false
        when (id) {
            R.id.home -> navController.navigate(R.id.homeFragment)
            R.id.favorite -> navController.navigate(R.id.favFragment)
            R.id.profile -> navController.navigate(R.id.profileFragment)
            R.id.cart -> navController.navigate(R.id.cartFragment)
            else -> return false
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}