package com.example.mobileclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.mobileclient.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        //SPACE TO ADD ONCLICK LISTENERS ETC.
        binding.forgotPassword.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.login_to_forgotpassword)
        }
        binding.loginButton.setOnClickListener {
            getAccessToken()
        }
        return view
    }

    fun getAccessToken(){
        var service: GetDataService =
            RetrofitClientInstance.getRetrofitInstance()!!.create(GetDataService::class.java)

        var email: String = binding.emailFieldText.text.toString()
        var password: String = binding.passwordFieldText.text.toString()

        var call: Call<AccessToken> = service.getAccessToken("backend", "password", "SpQPBHILk6ag8lf37SBnZNpCbDt50UBG", "openid", email, password)
        call.enqueue(object: Callback<AccessToken>{
            override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                var accessToken: AccessToken? = response.body()
                if(response.isSuccessful){
                    Navigation.findNavController(view!!).navigate(R.id.login_to_loggedin)
                }else{
                    Toast.makeText(context, accessToken.toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                Toast.makeText(context, "Error with login", Toast.LENGTH_LONG).show()
            }

        })
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