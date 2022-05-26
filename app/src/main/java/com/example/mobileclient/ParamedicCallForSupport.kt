package com.example.mobileclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.mobileclient.databinding.FragmentParamedicCallForSupportBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [ParamedicCallForSupport.newInstance] factory method to
 * create an instance of this fragment.
 */
class ParamedicCallForSupport : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentParamedicCallForSupportBinding? = null
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
        _binding = FragmentParamedicCallForSupportBinding.inflate(inflater, container, false)

        val check1 = binding.checkBoxA
        val check2 = binding.checkBoxB
        val check3 = binding.checkBoxC

        binding.button.setOnClickListener{
            if (check1.isChecked){
                Toast.makeText(context, "Ambulance", Toast.LENGTH_LONG).show()
            } else {}
            if (check2.isChecked){
                Toast.makeText(context, "Fire Truck", Toast.LENGTH_LONG).show()
            } else {}
            if (check3.isChecked){
                Toast.makeText(context, "Police", Toast.LENGTH_LONG).show()
            } else {}
        }

        val view = binding.root

        binding.button2.setOnClickListener{
            Navigation.findNavController(view).navigate(com.example.mobileclient.R.id.paramedicScreen)
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
         * @return A new instance of fragment ParamedicCallForSupport.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ParamedicCallForSupport().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}