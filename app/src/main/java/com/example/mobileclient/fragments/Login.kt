package com.example.mobileclient.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.ParamedicActivity
import com.example.mobileclient.R
import com.example.mobileclient.UserActivity
import com.example.mobileclient.databinding.FragmentLoginBinding
import com.example.mobileclient.model.UserViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 */
class Login : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentLoginBinding? = null
    private val sharedViewModel: UserViewModel by activityViewModels()

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        //SPACE TO ADD ONCLICK LISTENERS ETC.
        binding.forgotPassword.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_login_to_forgotPassword)
        }
        binding.loginButton.setOnClickListener {
            val userActivity = Intent(context, UserActivity::class.java)
            Toast.makeText(context, getString(R.string.login_toast), Toast.LENGTH_LONG).show()
            //Here we should apply putExtra method with auth token from login response
            startActivity(userActivity)

            /*
            Log.d("Email", binding.emailFieldText.text.toString())
            Log.d("Password", binding.passwordFieldText.text.toString())
            val credentials = Credentials(
                binding.emailFieldText.text.toString().trim(),
                binding.passwordFieldText.text.toString().trim()
            )
            Log.d("Credentials", credentials.toString())
            if (credentials.username.isNotEmpty()) {
            sharedViewModel.getLoginResponse(credentials)
            sharedViewModel.loginResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    Log.d("Login Response", response.body().toString())
                    Log.d("Response Code", response.code().toString())
                    Toast.makeText(context, "Login successful", LENGTH_LONG).show()

                    Navigation.findNavController(view).navigate(R.id.login_to_loggedin)

            } else {
                    Toast.makeText(context, "Login error" + response.code(), LENGTH_LONG).show()
                    Log.d("Login Response", response.body().toString())
                    Log.d("Response Code: ", response.code().toString())
                }


            }
        }else{
                Toast.makeText(context, "No connection", LENGTH_SHORT).show()
            }

             */
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Login.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Login().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}