package com.example.mobileclient.fragments

import android.content.Context
import android.content.SharedPreferences
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
import com.example.mobileclient.model.Tutorial
import com.example.mobileclient.viewmodels.TutorialsViewModel
import com.example.mobileclient.viewmodels.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoggedInScreen : Fragment(), AdapterView.OnItemSelectedListener,
    TutorialsAdapter.OnItemClickListener {
    private var _binding: FragmentLoggedInScreenBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()
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
        binding.tutorialsGrid.adapter =
            TutorialsAdapter(requireContext(),tutorialsEmpty, this, ratingBarChangeListener)
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        val token: String = sharedPreferences.getString("token", "")!!
        getTutorialsFromAPI()
        binding.refresh.setOnRefreshListener {
            getTutorialsFromAPI()
            binding.refresh.isRefreshing = false
        }
        if (requireActivity().getPreferences(Context.MODE_PRIVATE)
                .getBoolean("createIncidentON", false)
        ) {
            binding.addIncidentButton.hide()
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
            viewModel = userViewModel
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
                    TutorialsAdapter(requireContext(),
                        it, this, ratingBarChangeListener
                    )
                }
            }
            "FILE_EMERGENCE" -> {
                val filteredEmergenceTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "FILE_EMERGENCE" }
                currentlyDisplayedTutorials = filteredEmergenceTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(requireContext(),
                        it, this, ratingBarChangeListener
                    )
                }
            }
            "COURSE" -> {
                val filteredCourseTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "COURSE" }
                currentlyDisplayedTutorials = filteredCourseTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(requireContext(),
                        it, this, ratingBarChangeListener
                    )
                }
            }
            "GUIDE" -> {
                val filteredGuideTutorials =
                    tutorialsFromAPI?.filter { it.tutorialType == "GUIDE" }
                currentlyDisplayedTutorials = filteredGuideTutorials
                binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
                    TutorialsAdapter(requireContext(),
                        it, this, ratingBarChangeListener
                    )
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        currentlyDisplayedTutorials = tutorialsFromAPI
        binding.tutorialsGrid.adapter = currentlyDisplayedTutorials?.let {
            TutorialsAdapter(requireContext(),
                it, this, ratingBarChangeListener
            )
        }
    }

    override fun onItemClick(position: Int) {
        Log.d("Tutorial clicked", "User clicked tutorial $position")
        val tutorial = currentlyDisplayedTutorials?.get(position)
        tutorialsViewModel.pickedTutorial = tutorial
        //navigate to tutorial html view
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_loggedInScreen_to_tutorialHtmlView)
    }

    private fun getTutorialsFromAPI() {
        tutorialsViewModel.getTutorials()
        tutorialsViewModel.getTutorialsResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                tutorialsFromAPI = response.body()
                currentlyDisplayedTutorials = tutorialsFromAPI
                Log.d("getTutorialsResponse", response.code().toString())
                binding.tutorialsGrid.adapter =
                    currentlyDisplayedTutorials?.let {
                        TutorialsAdapter(
                            requireContext(),
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
                val tutorial = currentlyDisplayedTutorials?.get(ratingBar.tag as Int)
                val tutorialId = tutorial?.id

                ratingBar.rating = rating
            }
        }
}