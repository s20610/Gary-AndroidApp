package com.example.mobileclient.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentSplashScreenBinding


class MainScreen : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.splashScreenLogin.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splashScreen_to_login)
        }
        binding.splashScreenSignin.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splashScreen_to_register)
        }
        binding.guestButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splashScreen_to_guestScreen)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}