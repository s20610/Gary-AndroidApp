package com.example.mobileclient.fragments

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
import com.example.mobileclient.databinding.FragmentGuestScreenBinding
import com.example.mobileclient.model.Tutorial
import com.example.mobileclient.util.Constants.Companion.tutorialsEmpty
import com.example.mobileclient.util.checkIfInternetAvailable
import com.example.mobileclient.viewmodels.TutorialsViewModel

class GuestScreen : Fragment(),
    TutorialsAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private var _binding: FragmentGuestScreenBinding? = null
    private val tutorialsViewModel: TutorialsViewModel by activityViewModels()
    private var tutorialsFromAPI: ArrayList<Tutorial>? = ArrayList()
    private var currentlyDisplayedTutorials: ArrayList<Tutorial>? = ArrayList()

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
            TutorialsAdapter(requireContext(), tutorialsEmpty, this, ratingBarChangeListener,true)
//        tutorialsAdapter.setTutorials(tutorialsEmpty)
        binding.tutorialsGrid.adapter = tutorialsAdapter
        currentlyDisplayedTutorials?.add(Tutorial("1", "No tutorials available", "", "GENERAL", 0f, ""))
        getTutorialsFromAPI()
        binding.refresh.setOnRefreshListener {
            getTutorialsFromAPI()
            binding.refresh.isRefreshing = false
        }
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.title) {
                getString(R.string.sign_up) -> {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_guestScreen_to_register)
                    true
                }
                getString(R.string.log_in) -> {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_guestScreen_to_login)
                    true
                }
                else -> false
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

    override fun onItemClick(position: Int) {
        Toast.makeText(context, getString(R.string.cantWatchTutorials), Toast.LENGTH_SHORT)
            .show()
        Log.d("Tutorial clicked", "Guest clicked tutorial $position")
    }

    private fun getTutorialsFromAPI() {
        if (checkIfInternetAvailable(requireContext())) {
            tutorialsViewModel.getTutorials()
            tutorialsViewModel.getTutorialsResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    tutorialsFromAPI = response.body() as ArrayList<Tutorial>?
                    currentlyDisplayedTutorials = tutorialsFromAPI
                    Log.d("getTutorialsResponseBody", response.body().toString())
                    binding.tutorialsGrid.adapter =
                        currentlyDisplayedTutorials?.let {
                            TutorialsAdapter(
                                requireContext(),
                                it,
                                this,
                                ratingBarChangeListener,true
                            )
                        }
                } else {
                    Log.d("getTutorialsResponseBody", response.body().toString())
                    Log.d("getTutorialsResponseCode", response.code().toString())
                }
            }
        } else {
            tutorialsFromAPI?.add(Tutorial("1", "No tutorials available", "", "GENERAL", 0f, ""))
            Toast.makeText(requireContext(), getString(R.string.no_internet), Toast.LENGTH_LONG)
                .show()
        }
    }

    private val ratingBarChangeListener =
        RatingBar.OnRatingBarChangeListener { ratingBar, _, fromUser ->
            if (fromUser) {
                Toast.makeText(context, getString(R.string.cantRateTutorials), Toast.LENGTH_SHORT)
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
                (binding.tutorialsGrid.adapter as TutorialsAdapter).setTutorials(currentlyDisplayedTutorials!!)
                binding.tutorialsGrid.adapter?.notifyDataSetChanged()
            }
            general -> {
                val filteredEmergenceTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "GENERAL" }
                currentlyDisplayedTutorials = filteredEmergenceTutorials as ArrayList<Tutorial>?
                (binding.tutorialsGrid.adapter as TutorialsAdapter).setTutorials(currentlyDisplayedTutorials!!)
                binding.tutorialsGrid.adapter?.notifyDataSetChanged()
            }
            inCaseOfDeath -> {
                val filteredInCaseOfDeathTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "IN_CASE_OF_DEATH_EMERGENCY" }
                currentlyDisplayedTutorials = filteredInCaseOfDeathTutorials as ArrayList<Tutorial>?
                (binding.tutorialsGrid.adapter as TutorialsAdapter).setTutorials(currentlyDisplayedTutorials!!)
                binding.tutorialsGrid.adapter?.notifyDataSetChanged()
            }
            course -> {
                val filteredCourseTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "COURSE" }
                currentlyDisplayedTutorials = filteredCourseTutorials as ArrayList<Tutorial>?
                (binding.tutorialsGrid.adapter as TutorialsAdapter).setTutorials(currentlyDisplayedTutorials!!)
                binding.tutorialsGrid.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        currentlyDisplayedTutorials = tutorialsFromAPI
        (binding.tutorialsGrid.adapter as TutorialsAdapter).setTutorials(currentlyDisplayedTutorials!!)
        binding.tutorialsGrid.adapter?.notifyDataSetChanged()
    }
}