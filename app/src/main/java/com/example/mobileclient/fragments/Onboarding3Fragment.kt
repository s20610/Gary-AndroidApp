package com.example.mobileclient.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileclient.LandingActivity
import com.example.mobileclient.databinding.Onboarding3Binding

class Onboarding3Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = Onboarding3Binding.inflate(inflater, container, false)
        binding.finishButton.setOnClickListener {
            val landingActivity = Intent(context, LandingActivity::class.java)
            startActivity(landingActivity)
        }
        return binding.root
    }
}