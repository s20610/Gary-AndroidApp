package com.example.mobileclient.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mobileclient.R
import com.example.mobileclient.databinding.ActivityParamedicBinding
import com.example.mobileclient.util.Constants.Companion.USER_EMAIL_TO_PREFS
import com.example.mobileclient.util.Constants.Companion.USER_INFO_PREFS
import com.example.mobileclient.util.Constants.Companion.USER_ROLE_TO_PREFS
import com.example.mobileclient.util.Constants.Companion.USER_TOKEN_TO_PREFS
import com.google.android.material.appbar.MaterialToolbar


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
                getString(R.string.breakText) -> {
                    it.isChecked = true
                    navController.navigate(R.id.ambulanceBreak)
                }
                getString(R.string.log_out) -> {
                    it.isChecked = true
                    val sharedPreferences =
                        getSharedPreferences(USER_INFO_PREFS, Context.MODE_PRIVATE)
                    sharedPreferences.edit().remove(USER_ROLE_TO_PREFS).remove(USER_TOKEN_TO_PREFS)
                        .remove(USER_EMAIL_TO_PREFS).commit()
                    Log.d(
                        "User prefs",
                        getSharedPreferences(USER_INFO_PREFS, MODE_PRIVATE).all.toString()
                    )
                    val intent = Intent(this, LandingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
        binding.bottomNavigation?.setOnItemSelectedListener {
            it.isChecked = true
            val menuString = getString(R.string.menu_equipment)
            val victimString = getString(R.string.menu_victim)
            val supportString = getString(R.string.menu_support)
            when (it.toString()) {
                menuString -> {
                    it.isChecked = true
                    navController.navigate(R.id.checkEquipment)
                }
                victimString -> {
                    it.isChecked = true
                    navController.navigate(R.id.addVictimInfo)
                }
                supportString -> {
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