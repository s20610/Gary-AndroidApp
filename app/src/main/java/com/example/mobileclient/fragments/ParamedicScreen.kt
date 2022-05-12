package com.example.mobileclient.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentIncidentBinding
import com.example.mobileclient.databinding.FragmentLoggedInScreenBinding
import com.example.mobileclient.databinding.FragmentMedicalInfoMainBinding
import com.example.mobileclient.databinding.FragmentParamedicScreenBinding
import com.example.mobileclient.model.UserViewModel
import com.google.android.material.color.MaterialColors
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [ParamedicScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class ParamedicScreen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentParamedicScreenBinding? = null
    private val sharedViewModel: UserViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentParamedicScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatted = current.format(formatter)
        binding.dayField.text = formatted
        binding.checkinButton.setOnClickListener{
            if(binding.checkinButton.text == "Check In"){
                binding.checkinButton.text = "Finish Shift"
            }else {
                binding.checkinButton.text = "Check In"
            }
        }


        binding.button3.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_paramedicScreen_to_paramedicCallForSupport2)
        }



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ParamedicScreen.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ParamedicScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}