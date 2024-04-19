package com.example.foodapp.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import com.example.foodapp.data.datasource.auth.AuthDataSource
import com.example.foodapp.data.datasource.auth.FirebaseAuthDataSource
import com.example.foodapp.data.repository.UserRepository
import com.example.foodapp.data.repository.UserRepositoryImpl
import com.example.foodapp.data.source.firebase.FirebaseService
import com.example.foodapp.data.source.firebase.FirebaseServiceImpl
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.presentation.login.LoginActivity
import com.example.foodapp.presentation.register.RegisterViewModel
import com.example.foodapp.utils.GenericViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(MainViewModel(r))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{controller, destination, arguments ->
            when(destination.id){
                R.id.menu_tab_profile -> {
                    if(!viewModel.isLoggedIn()) {
                        navigateToLogin()
                        controller.popBackStack(R.id.menu_tab_home, false)
                    }
                }
            }
        }
    }
    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }
}