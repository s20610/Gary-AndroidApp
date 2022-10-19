package com.example.mobileclient.fragments

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.mobileclient.databinding.FragmentEquipmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Equipment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Equipment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentEquipmentBinding? = null
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

        _binding = FragmentEquipmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val names = arrayOf(
            "Karetka1",
            "Karetka2",
            "Karetka3",
            "Karetka4",
            "Karetka5",
            "Karetka6",
            "Karetka7",
            "Karetka8",
            "Karetka9",
            "Karetka10",
            "Karetka11",
            "Karetka12",
            "Karetka13",
            "Karetka14",
            "Karetka15",
            "Karetka16",
            "Karetka17",
            "Karetka18",
            "Karetka19",
            "Karetka20"
        )

        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.simple_list_item_1, names)
        binding.ambulanceList.adapter=arrayAdapter

        binding.ambulanceList.setOnItemClickListener{ adapterView, view, i, l ->
            Navigation.findNavController(view)
                .navigate(com.example.mobileclient.R.id.action_equipment_to_checkEquipment)

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
         * @return A new instance of fragment Equipment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Equipment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}