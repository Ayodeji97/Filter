package com.mylearning.devplacement.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mylearning.devplacement.R
import com.mylearning.devplacement.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
/* Main activity */
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /* Function responsible for controlling the app bar title and navigations */
    private fun setUpViews () {
        // find the nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment

        navController = navHostFragment.findNavController()

        var appConfiguration = AppBarConfiguration(navController.graph)

        // Setting Up ActionBar with Navigation Controller
        setupActionBarWithNavController(navController, appConfiguration)

    }

}