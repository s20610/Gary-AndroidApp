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
import com.example.mobileclient.model.Tutorial
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
        val tutorialsEmpty: List<Tutorial> = mutableListOf(
            Tutorial(
                "1",
                "Tutorial 1",
                "https://miro.medium.com/max/480/1*QiE4-0MPslYPvx2Fit1NIQ.jpeg",
                "COURSE",
                0.2f,
                ""
            ),
            Tutorial(
                "2",
                "Tutorial 2",
                "https://miro.medium.com/max/480/1*QiE4-0MPslYPvx2Fit1NIQ.jpeg",
                "FILE_EMERGENCE",
                0.5f,
                ""
            ),
            Tutorial(
                "3",
                "Tutorial 3",
                "https://miro.medium.com/max/480/1*QiE4-0MPslYPvx2Fit1NIQ.jpeg",
                "GUIDE",
                0.2f,
                ""
            ),
            Tutorial(
                "4",
                "Tutorial 4",
                "https://miro.medium.com/max/480/1*QiE4-0MPslYPvx2Fit1NIQ.jpeg",
                "FILE_EMERGENCE",
                0.7f,
                ""
            ),
            Tutorial(
                "5",
                "Tutorial 5",
                "https://miro.medium.com/max/480/1*QiE4-0MPslYPvx2Fit1NIQ.jpeg",
                "COURSE",
                0.25f,
                ""
            ),
        )
        var tutorialsAdapter =
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
        RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (fromUser) {
                Toast.makeText(context, "You can't rate tutorials as guest", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        p0?.getItemAtPosition(p2).toString()
        when (p0?.getItemAtPosition(p2).toString()) {
            "All Tutorials" -> {
                currentlyDisplayedTutorials = tutorialsFromAPI
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        requireContext(),
                        it, this, ratingBarChangeListener
                    )
                }
            }
            "FILE_EMERGENCE" -> {
                val filteredEmergenceTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "FILE_EMERGENCE" }
                currentlyDisplayedTutorials = filteredEmergenceTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        requireContext(),
                        it, this, ratingBarChangeListener
                    )
                }
            }
            "COURSE" -> {
                val filteredCourseTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "COURSE" }
                currentlyDisplayedTutorials = filteredCourseTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        requireContext(),
                        it, this, ratingBarChangeListener
                    )
                }
            }
            "GUIDE" -> {
                val filteredGuideTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "GUIDE" }
                currentlyDisplayedTutorials = filteredGuideTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(
                        requireContext(),
                        it, this, ratingBarChangeListener
                    )
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        currentlyDisplayedTutorials = tutorialsFromAPI
        binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
            TutorialsAdapter(
                requireContext(),
                it, this, ratingBarChangeListener
            )
        }
    }
}