package com.example.mobileclient.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.adapter.TutorialsAdapter
import com.example.mobileclient.databinding.FragmentLoggedInScreenBinding
import com.example.mobileclient.model.*
import com.example.mobileclient.viewmodels.TutorialsViewModel
import com.example.mobileclient.viewmodels.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoggedInScreen : Fragment(), AdapterView.OnItemSelectedListener,
    TutorialsAdapter.OnItemClickListener {
    private var _binding: FragmentLoggedInScreenBinding? = null
    private val sharedViewModel: UserViewModel by activityViewModels()
    private val tutorialsViewModel: TutorialsViewModel by activityViewModels()
    private var tutorialsFromAPI: List<Tutorial>? = null
    private var currentlyDisplayedTutorials: List<Tutorial>? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

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
        binding.tutorialsGrid.adapter =
            TutorialsAdapter(tutorialsEmpty, this, ratingBarChangeListener)
        val intent = Intent()
        val email = intent.getStringExtra("E-mail")
//        Toast.makeText(context, "Email zalogowano: $email", Toast.LENGTH_LONG).show()
        getTutorialsFromAPI()
        binding.refresh.setOnRefreshListener {
            getTutorialsFromAPI()
            binding.refresh.isRefreshing = false
        }
        binding.addIncidentButton.setOnClickListener {
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1).setTitle(getString(R.string.create_incident_popup_title))
                    .setMessage(getString(R.string.create_incident_popup_alert))
                    .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                        dialog.cancel()
                    }
                    .setPositiveButton(getString(R.string.accept)) { _, _ ->
                        Navigation.findNavController(view)
                            .navigate(R.id.action_loggedInScreen_to_incident)
                    }
                    .show()
            }
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

    //Methods for filterMenu from AdapterView.OnItemSelectedListener
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        p0?.getItemAtPosition(p2).toString()
        when (p0?.getItemAtPosition(p2).toString()) {
            "All Tutorials" -> {
                currentlyDisplayedTutorials = tutorialsFromAPI
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        it, this, ratingBarChangeListener
                    )
                }
            }
            "FILE_EMERGENCE" -> {
                val filteredEmergenceTutorials =
                    tutorialsFromAPI?.filter { it.tutorialKind == "FILE_EMERGENCE" }
                currentlyDisplayedTutorials = filteredEmergenceTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        it, this, ratingBarChangeListener
                    )
                }
            }
            "COURSE" -> {
                val filteredCourseTutorials =
                    tutorialsFromAPI?.filter { it.tutorialKind == "COURSE" }
                currentlyDisplayedTutorials = filteredCourseTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        it, this, ratingBarChangeListener
                    )
                }
            }
            "GUIDE" -> {
                val filteredGuideTutorials =
                    tutorialsFromAPI?.filter { it.tutorialKind == "GUIDE" }
                currentlyDisplayedTutorials = filteredGuideTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        it, this, ratingBarChangeListener
                    )
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        currentlyDisplayedTutorials = tutorialsFromAPI
        binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
            TutorialsAdapter(
                it, this, ratingBarChangeListener
            )
        }
    }

    override fun onItemClick(position: Int) {
        Log.d("Tutorial clicked", "User clicked tutorial $position")
        Toast.makeText(requireContext(), "Tutorial $position", Toast.LENGTH_SHORT).show()
    }

    private fun getTutorialsFromAPI() {
        tutorialsViewModel.getTutorials()
        tutorialsViewModel.getTutorialsResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                tutorialsFromAPI = response.body()
                currentlyDisplayedTutorials = tutorialsFromAPI
                Log.d("getTutorialsResponseBody", response.body().toString())
                binding.tutorialsGrid.adapter =
                    currentlyDisplayedTutorials?.let {
                        TutorialsAdapter(
                            it,
                            this,
                            ratingBarChangeListener
                        )
                    }
                binding.filterMenu.setSelection(0)
            } else {
                Toast.makeText(context, "Tutorials error ${response.code()}", Toast.LENGTH_SHORT)
                    .show()
                Log.d("getTutorialsResponseBody", response.body().toString())
                Log.d("getTutorialsResponseCode", response.code().toString())
            }
        }
    }

    private val ratingBarChangeListener =
        RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (fromUser) {
                Toast.makeText(context, "Thanks for rating our tutorial!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
}