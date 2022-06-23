package com.example.mobileclient.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.adapter.TutorialsAdapter
import com.example.mobileclient.databinding.FragmentLoggedInScreenBinding
import com.example.mobileclient.model.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoggedInScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoggedInScreen : Fragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentLoggedInScreenBinding? = null
    private val sharedViewModel: UserViewModel by activityViewModels()
    private val tutorialsViewModel: TutorialsViewModel by activityViewModels()
    var tutorialsFromAPI: List<Tutorial>? = null
    var currentlyDisplayedTutorials: List<Tutorial>? = null

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
        // Inflate the layout for this fragment
        _binding = FragmentLoggedInScreenBinding.inflate(inflater, container, false)


        //val view1 = inflater.inflate(R.layout.user_navigation_drawer_header, null) as View
        //view1.findViewById<TextView>(R.id.email_field_text).text = email

        val view = binding.root
        val tutorialsEmpty: List<Tutorial> = mutableListOf(
            Tutorial("1", "Tutorial 1", "COURSE", 5.0f),
            Tutorial("2", "Tutorial 2", "FILE_EMERGENCE", 5.0f),
            Tutorial("3", "Tutorial 3", "GUIDE", 5.0f),
            Tutorial("4", "Tutorial 4", "FILE_EMERGENCE", 5.0f),
            Tutorial("5", "Tutorial 5", "COURSE", 5.0f),
        )
        binding.tutorialsGrid.adapter = TutorialsAdapter(tutorialsEmpty)
        val intent = Intent()
        val email = intent.getStringExtra("E-mail")
//        Toast.makeText(context, "Email zalogowano: $email", Toast.LENGTH_LONG).show()
        tutorialsViewModel.getTutorials()
        tutorialsViewModel.getTutorialsResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                tutorialsFromAPI = response.body()
                currentlyDisplayedTutorials = tutorialsFromAPI
                Log.d("Body", response.body().toString())
                binding.tutorialsGrid.adapter =
                    currentlyDisplayedTutorials?.let { TutorialsAdapter(it) }
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        binding.refresh.setOnRefreshListener {
            tutorialsViewModel.getTutorials()
            tutorialsViewModel.getTutorialsResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    tutorialsFromAPI = response.body()
                    currentlyDisplayedTutorials = tutorialsFromAPI
                    Log.d("Body", response.body().toString())
                    binding.tutorialsGrid.adapter =
                        currentlyDisplayedTutorials?.let { TutorialsAdapter(it) }
                    binding.filterMenu.setSelection(0)
                    binding.refresh.isRefreshing = false
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
            binding.refresh.isRefreshing = false
        }
        binding.addIncidentButton.setOnClickListener {
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1).setTitle("Create Incident?")
                    .setMessage("Unjustified ambulance call may result in a fine. Are you sure?")
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.cancel()
                    }
                    .setPositiveButton("Accept") { dialog, which ->
                        Navigation.findNavController(view)
                            .navigate(R.id.action_loggedInScreen_to_incident)
                    }
                    .show()
            }
        }

        binding.settingsTmpButton.setOnClickListener{
            Navigation.findNavController(view)
                .navigate(R.id.action_loggedInScreen_to_user_settings)
        }

        //Filter menu
        val filterMenu: Spinner = binding.filterMenu
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filterTutorialsArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            filterMenu.adapter = adapter
        }
        filterMenu.onItemSelectedListener = this
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
         * @return A new instance of fragment LoggedInScreen.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoggedInScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //Methods for filterMenu from AdapterView.OnItemSelectedListener
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        p0?.getItemAtPosition(p2).toString()
        when (p0?.getItemAtPosition(p2).toString()) {
            "All Tutorials" -> {
                currentlyDisplayedTutorials = tutorialsFromAPI
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        it
                    )
                }
            }
            "FILE_EMERGENCE" -> {
                val filteredEmergenceTutorials =
                    tutorialsFromAPI?.filter { it.tutorialKind == "FILE_EMERGENCE" }
                currentlyDisplayedTutorials = filteredEmergenceTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        it
                    )
                }
            }
            "COURSE" -> {
                val filteredCourseTutorials =
                    tutorialsFromAPI?.filter { it.tutorialKind == "COURSE" }
                currentlyDisplayedTutorials = filteredCourseTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        it
                    )
                }
            }
            "GUIDE" -> {
                val filteredGuideTutorials =
                    tutorialsFromAPI?.filter { it.tutorialKind == "GUIDE" }
                currentlyDisplayedTutorials = filteredGuideTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        it
                    )
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        currentlyDisplayedTutorials = tutorialsFromAPI
        binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
            TutorialsAdapter(
                it
            )
        }
    }
}