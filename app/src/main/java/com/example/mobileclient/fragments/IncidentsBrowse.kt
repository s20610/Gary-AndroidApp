package com.example.mobileclient.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentIncidentsBrowseBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private var _binding: FragmentIncidentsBrowseBinding? =null
private val binding get() = _binding!!

/**
 * A simple [Fragment] subclass.
 * Use the [IncidentsBrowse.newInstance] factory method to
 * create an instance of this fragment.
 */
class IncidentsBrowse : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment

        _binding= FragmentIncidentsBrowseBinding.inflate(inflater, container, false)

        val names = arrayOf("Zgloszenie1  10-10-2022", "Zgloszenie2  10-10-2022", "Zgloszenie3  10-10-2022", "Zgloszenie4  10-10-2022", "Zgloszenie5  10-10-2022", "Zgloszenie6  10-10-2022", "Zgloszenie7  10-10-2022",
            "Zgloszenie8  10-10-2022", "Zgloszenie9  10-10-2022" , "Zgloszenie10  10-10-2022", "Zgloszenie11  10-10-2022", "Zgloszenie12  10-10-2022", "Zgloszenie13  10-10-2022", "Zgloszenie14  10-10-2022", "Zgloszenie15  10-10-2022",
            "Zgloszenie16  10-10-2022" , "Zgloszenie17  10-10-2022", "Zgloszenie18  10-10-2022", "Zgloszenie19  10-10-2022", "Zgloszenie20  10-10-2022", "Zgloszenie21  10-10-2022")

        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
        binding.listView.adapter = arrayAdapter

        binding.listView.setOnItemClickListener { adapterView, view, i, l ->
            Navigation.findNavController(view).navigate(R.id.action_incidentsBrowse2_to_showIncident)

        }

        val view = binding.root
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IncidentsBrowse.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IncidentsBrowse().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}