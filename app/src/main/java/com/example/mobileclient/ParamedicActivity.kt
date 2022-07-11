package com.example.mobileclient

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.mobileclient.databinding.ActivityParamedicBinding


class ParamedicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParamedicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParamedicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val tp = ThreadPolicy.LAX
        StrictMode.setThreadPolicy(tp)

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
        binding.navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            when (it.toString()) {
                "Map" -> {
                    it.isChecked = true
                    navController.navigate(R.id.paramedicScreen)
                }
                "Break" -> {
                    it.isChecked = true
                    navController.navigate(R.id.ambulanceBreak)
                }
                "Log out" -> {
                    it.isChecked = true
                    Toast.makeText(this, "Logout not yet implemented", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.paramedicScreen)
                }
            }
            true
        }
        binding.bottomNavigation?.setOnItemSelectedListener {
            it.isChecked = true
            if (it.toString() == "Equipment") {
                navController.navigate(R.id.checkEquipment)
            } else if (it.toString() == "Victim") {
                navController.navigate(R.id.addVictimInfo)
            } else if (it.toString() == "Support") {
                navController.navigate(R.id.paramedicCallForSupport2)
            }
            else if (it.toString() == "Map") {
                navController.navigate(R.id.paramedicScreen)
            }
            true
        }
    }
}