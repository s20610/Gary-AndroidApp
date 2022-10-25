package com.example.mobileclient.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.activities.ParamedicActivity
import com.example.mobileclient.R
import com.example.mobileclient.activities.UserActivity
import com.example.mobileclient.databinding.FragmentLoginBinding
import com.example.mobileclient.model.Credentials
import com.example.mobileclient.viewmodels.UserViewModel

class Login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val sharedViewModel: UserViewModel by activityViewModels()

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.forgotPassword.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_login_to_forgotPassword)
        }
        binding.loginButton.setOnClickListener {
            val email = binding.emailFieldText.text.toString().trim()
            val password = binding.passwordFieldText.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val credentials = Credentials(
                    email,
                    password
                )
                sharedViewModel.getLoginResponse(credentials)
                sharedViewModel.loginResponse.observe(viewLifecycleOwner) { response ->
                    if (response.isSuccessful) {
                        Log.d("Login Response", response.body().toString())
                        Log.d("Response Code", response.code().toString())
                        Toast.makeText(context, "Login successful", LENGTH_LONG).show()
                        addEmailAndTokenToSharedPref(email,response.body()!!.token)
                        val userActivity = Intent(context, UserActivity::class.java)
                        startActivity(userActivity)
                    } else {
                        Toast.makeText(context, "Login error" + response.code(), LENGTH_LONG).show()
                        Log.d("Login Response", response.body().toString())
                        Log.d("Response Code: ", response.code().toString())
                    }
                }
            }
        }
        binding.paramedicButton.setOnClickListener {
//            Log.d("Email", binding.emailFieldText.text.toString())
//            Log.d("Password", binding.passwordFieldText.text.toString())
//            val credentials = Credentials(
//                binding.emailFieldText.text.toString().trim(),
//                binding.passwordFieldText.text.toString().trim()
//            )
//            Log.d("Credentials", credentials.toString())
//            if (credentials.username.isNotEmpty()) {
//                sharedViewModel.getLoginResponse(credentials)
//                sharedViewModel.loginResponse.observe(viewLifecycleOwner) { response ->
//                    if (response.isSuccessful) {
//                        Log.d("Login Response", response.body().toString())
//                        Log.d("Response Code", response.code().toString())
//                        Toast.makeText(context, "Login successful", LENGTH_LONG).show()
//
//                        Navigation.findNavController(view).navigate(R.id.action_login_to_paramedicScreen)
//                    } else {
//                        Toast.makeText(context, "Login error" + response.code(), LENGTH_LONG).show()
//                        Log.d("Login Response", response.body().toString())
//                        Log.d("Response Code: ", response.code().toString())
//                    }
//                }
//            }else{
//                Toast.makeText(context, "No connection", LENGTH_SHORT).show()
//            }
            val paramedicActivity = Intent(context, ParamedicActivity::class.java)
            //Here we should apply putExtra method with auth token from login response
            startActivity(paramedicActivity)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = sharedViewModel
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addEmailAndTokenToSharedPref(email: String, token: String) {
        val sharedPref = activity?.getSharedPreferences("userInfo",Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("email", email)
            putString("token", token)
            apply()
        }
    }
}