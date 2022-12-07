package com.example.mobileclient.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mobileclient.R
import com.example.mobileclient.databinding.ActivityParamedicBinding
import com.example.mobileclient.util.Constants.Companion.USER_INFO_PREFS


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
        val resources = Resources.getSystem()

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
                 resources.getString(R.string.ambulance_break)-> {
                    it.isChecked = true
                    navController.navigate(R.id.ambulanceBreak)
                }
                resources.getString(R.string.log_out) -> {
                    it.isChecked = true
                    getSharedPreferences(USER_INFO_PREFS, MODE_PRIVATE).edit().clear().apply()
                    val intent = Intent(this, LandingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
        binding.bottomNavigation?.setOnItemSelectedListener {
            it.isChecked = true
            when (it.toString()) {
                resources.getString(R.string.menu_equipment) -> {
                    it.isChecked = true
                    navController.navigate(R.id.checkEquipment)
                }
                resources.getString(R.string.menu_victim) -> {
                    it.isChecked = true
                    navController.navigate(R.id.addVictimInfo)
                }
                resources.getString(R.string.menu_support) -> {
                    it.isChecked = true
                    navController.navigate(R.id.paramedicCallForSupport2)
                }
                else -> {
                    it.isChecked = true
                    navController.navigate(R.id.paramedicScreen)
                }
            }
            true
        }
        com.example.mobileclient.util.checkPermission(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            ACCESS_FINE_LOCATION_CODE,
            this, this
        )

    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
        } else {
            findNavController(R.id.fragmentContainerView).navigateUp()
        }
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