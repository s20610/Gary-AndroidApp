package com.example.mobileclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.mobileclient.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navigationView.setCheckedItem(R.id.nav_tutorials)
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
        binding.navigationView.getHeaderView(0).setOnClickListener {
            navController
                .navigate((R.id.userInfo))
            binding.drawerLayout.close()
        }
        binding.navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            when {
                it.toString() == "Log out" -> {
                    TODO("Go from UserActivity to LandingActivity")
                }
                it.toString() == "User Details" -> {
                    binding.navigationView.setCheckedItem(R.id.nav_user_details)
                    navController.navigate((R.id.medicalInfoMain))
                }
                it.toString() == "Reported Incidents" -> {
                    binding.navigationView.setCheckedItem(R.id.nav_user_incidents)
                    navController.navigate((R.id.incidentsBrowse))
                }
                it.toString() == "Map" -> {
                    binding.navigationView.setCheckedItem(R.id.nav_map)
                    navController.navigate((R.id.facilitiesMap))
                }
                it.toString() == "Tutorials" -> {
                    binding.navigationView.setCheckedItem(R.id.nav_tutorials)
                    navController.navigate((R.id.loggedInScreen))
                }
            }
            binding.drawerLayout.close()
            true
        }
    }
}