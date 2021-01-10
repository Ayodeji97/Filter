package com.mylearning.devplacement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mylearning.devplacement.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var ui : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)

        setUpViews()

        return setContentView(ui.root)

    }

    private fun setUpViews () {

        // find the nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment

        navController = navHostFragment.findNavController()

        // setting nav controller with bottom navigation view
        ui.bottomNavView.setupWithNavController(navController)

        var appConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.carOwnersFragment,
                R.id.usersFragment
            )
        )
        // Setting Up ActionBar with Navigation Controller
        setupActionBarWithNavController(navController, appConfiguration)

    }

}