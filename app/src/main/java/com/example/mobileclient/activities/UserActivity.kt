package com.example.mobileclient.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mobileclient.R
import com.example.mobileclient.databinding.ActivityUserBinding
import com.example.mobileclient.util.Constants.Companion.USER_INFO_PREFS

class UserActivity : AppCompatActivity() {
    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val STORAGE_PERMISSION_CODE = 101
        private const val ACCESS_FINE_LOCATION_CODE = 102
    }

    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
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
                    getSharedPreferences(USER_INFO_PREFS , MODE_PRIVATE).edit().clear().apply()
                    val intent = Intent(this, LandingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                it.toString() == "Settings" -> {
                    binding.navigationView.setCheckedItem(R.id.nav_settings)
                    navController.navigate(R.id.user_settings)
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

                it.toString() == "Wyloguj się" -> {
                    getSharedPreferences(USER_INFO_PREFS , MODE_PRIVATE).edit().clear().apply()
                    val intent = Intent(this, LandingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                it.toString() == "Ustawienia" -> {
                    binding.navigationView.setCheckedItem(R.id.nav_settings)
                    navController.navigate(R.id.user_settings)
                }
                it.toString() == "Szczegóły użytkownika" -> {
                    binding.navigationView.setCheckedItem(R.id.nav_user_details)
                    navController.navigate((R.id.medicalInfoMain))
                }
                it.toString() == "Historia zgłoszeń" -> {
                    binding.navigationView.setCheckedItem(R.id.nav_user_incidents)
                    navController.navigate((R.id.incidentsBrowse))
                }
                it.toString() == "Mapa" -> {
                    binding.navigationView.setCheckedItem(R.id.nav_map)
                    navController.navigate((R.id.facilitiesMap))
                }
                it.toString() == "Tutoriale" -> {
                    binding.navigationView.setCheckedItem(R.id.nav_tutorials)
                    navController.navigate((R.id.loggedInScreen))
                }

            }
            binding.drawerLayout.close()
            true
        }
        com.example.mobileclient.util.checkPermission(
            android.Manifest.permission.CAMERA,
            CAMERA_PERMISSION_CODE,
            this, this
        )
        com.example.mobileclient.util.checkPermission(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            STORAGE_PERMISSION_CODE,
            this, this
        )
        com.example.mobileclient.util.checkPermission(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            ACCESS_FINE_LOCATION_CODE,
            this, this
        )
    }

    override fun recreate() {
        super.recreate()
        binding.navigationView.setCheckedItem(R.id.nav_tutorials)

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
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
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