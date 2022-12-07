package com.example.mobileclient.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.activities.ParamedicActivity
import com.example.mobileclient.R
import com.example.mobileclient.activities.UserActivity
import com.example.mobileclient.databinding.FragmentLoginBinding
import com.example.mobileclient.model.Credentials
import com.example.mobileclient.util.Constants.Companion.USER_EMAIL_TO_PREFS
import com.example.mobileclient.util.Constants.Companion.USER_INFO_PREFS
import com.example.mobileclient.util.Constants.Companion.USER_ROLE_TO_PREFS
import com.example.mobileclient.util.Constants.Companion.USER_TOKEN_TO_PREFS
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
            loginUser()
        }
        binding.paramedicButton.setOnClickListener {
            loginUser()
        }
        return view
    }

    private fun loginUser() {
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
                    addUserInfoToSharedPref(
                        email, response.body()!!.token,
                        response.body()!!.roles[0]
                    )
                    when {
                        response.body()!!.roles.contains("ROLE_MEDIC") -> {
                            val paramedicActivity = Intent(context, ParamedicActivity::class.java)
                            startActivity(paramedicActivity)
                            requireActivity().finish()
                        }
                        else -> {
                            val userActivity = Intent(context, UserActivity::class.java)
                            startActivity(userActivity)
                            requireActivity().finish()
                        }
                    }
                } else {
                    if (response.code() == 401) {
                        Toast.makeText(context, "Wrong email or password", LENGTH_SHORT).show()
                    }
                    Log.d("Login Response", response.body().toString())
                    Log.d("Response Code: ", response.code().toString())
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = sharedViewModel
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addUserInfoToSharedPref(email: String, token: String, role: String) {
        val sharedPref =
            activity?.getSharedPreferences(USER_INFO_PREFS, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(USER_EMAIL_TO_PREFS, email)
            putString(USER_TOKEN_TO_PREFS, token)
            putString(USER_ROLE_TO_PREFS, role)
            apply()
        }
    }
}