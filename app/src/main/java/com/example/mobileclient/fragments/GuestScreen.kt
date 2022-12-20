package com.example.mobileclient.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mobileclient.R
import com.example.mobileclient.adapter.TutorialsAdapter
import com.example.mobileclient.databinding.FragmentGuestScreenBinding
import com.example.mobileclient.model.Tutorial import com.example.mobileclient.util.Constants.Companion.tutorialsEmpty
import com.example.mobileclient.viewmodels.TutorialsViewModel

class GuestScreen : Fragment(),
    TutorialsAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private var _binding: FragmentGuestScreenBinding? = null
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
        _binding = FragmentGuestScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val tutorialsAdapter =
            TutorialsAdapter(requireContext(), tutorialsEmpty, this, ratingBarChangeListener)
        tutorialsAdapter.setTutorials(tutorialsEmpty)
        binding.tutorialsGrid.adapter = tutorialsAdapter
        getTutorialsFromAPI()
        binding.refresh.setOnRefreshListener {
            getTutorialsFromAPI()
            binding.refresh.isRefreshing = false
        }
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
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

    override fun onItemClick(position: Int) {
        Log.d("Tutorial clicked", "Guest clicked tutorial $position")
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
                            requireContext(),
                            it,
                            this,
                            ratingBarChangeListener
                        )
                    }
            } else {
                Log.d("getTutorialsResponseBody", response.body().toString())
                Log.d("getTutorialsResponseCode", response.code().toString())
            }
        }
    }

    private val ratingBarChangeListener =
        RatingBar.OnRatingBarChangeListener { _, _, fromUser ->
            if (fromUser) {
                Toast.makeText(context, "You can't rate tutorials as guest", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    //Methods for filterMenu from AdapterView.OnItemSelectedListener
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val stringArray = resources.getStringArray(R.array.filterTutorialsArray)
        val allTutorials = stringArray[0]
        val general = stringArray[1]
        val inCaseOfDeath = stringArray[2]
        val course = stringArray[3]
        when (p0?.getItemAtPosition(p2).toString()) {
            allTutorials -> {
                currentlyDisplayedTutorials = tutorialsFromAPI
                binding.tutorialsGrid.adapter = TutorialsAdapter(
                    requireContext(),
                    currentlyDisplayedTutorials!!,
                    this,
                    ratingBarChangeListener
                )
            }
            general -> {
                val filteredEmergenceTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "IN_CASE_OF_DEATH_EMERGENCY" }
                currentlyDisplayedTutorials = filteredEmergenceTutorials
                binding.tutorialsGrid.adapter = TutorialsAdapter(
                    requireContext(),
                    currentlyDisplayedTutorials!!,
                    this,
                    ratingBarChangeListener
                )
            }
            inCaseOfDeath -> {
                val filteredInCaseOfDeathTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "GENERAL" }
                currentlyDisplayedTutorials = filteredInCaseOfDeathTutorials
                binding.tutorialsGrid.adapter = TutorialsAdapter(
                    requireContext(),
                    currentlyDisplayedTutorials!!,
                    this,
                    ratingBarChangeListener
                )
            }
            course -> {
                val filteredCourseTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "COURSE" }
                currentlyDisplayedTutorials = filteredCourseTutorials
                binding.tutorialsGrid.adapter = TutorialsAdapter(
                    requireContext(),
                    currentlyDisplayedTutorials!!,
                    this,
                    ratingBarChangeListener
                )
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        currentlyDisplayedTutorials = tutorialsFromAPI
        binding.tutorialsGrid.adapter = TutorialsAdapter(
            requireContext(),
            currentlyDisplayedTutorials!!,
            this,
            ratingBarChangeListener
        )
    }
}