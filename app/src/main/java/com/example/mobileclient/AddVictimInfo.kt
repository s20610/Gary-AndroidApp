package com.example.mobileclient

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.mobileclient.databinding.FragmentAddVictimInfoBinding
import com.example.mobileclient.databinding.FragmentParamedicCallForSupportBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddVictimInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddVictimInfo : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddVictimInfoBinding? = null
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
        _binding = FragmentAddVictimInfoBinding.inflate(inflater, container, false)

        val incidents = arrayOf("Zadławienie", "Rana kłuta", "Wypadek samochodowy", "Inne")
        val victimState = arrayOf("Przytomna", "Nieprzytomna", "Nieoddychająca", "Inne")
        val arrayAdapter1 = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, incidents)
        val arrayAdapter2 = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, victimState)

        binding.autoCompleteTextView.setAdapter(arrayAdapter1)
        binding.autoCompleteTextView3.setAdapter(arrayAdapter2)

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
         * @return A new instance of fragment AddVictimInfo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddVictimInfo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}