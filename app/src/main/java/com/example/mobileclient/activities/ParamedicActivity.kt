package com.example.mobileclient.activities

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.mobileclient.R
import com.example.mobileclient.databinding.ActivityParamedicBinding


class ParamedicActivity : AppCompatActivity() {
    companion object {
        private const val ACCESS_FINE_LOCATION_CODE = 102
    }

    private lateinit var binding: ActivityParamedicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParamedicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

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
            } else if (it.toString() == "Map") {
                navController.navigate(R.id.paramedicScreen)
            }
            true
        }
        com.example.mobileclient.util.checkPermission(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            ACCESS_FINE_LOCATION_CODE,
            this, this
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACCESS_FINE_LOCATION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}