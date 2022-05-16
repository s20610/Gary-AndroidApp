package com.example.mobileclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
        binding.navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            if (it.toString() == "Log out") {
                TODO("Navigate from paramedic activity to landing activity")
            }
            true
        }
    }
}