package com.example.mobileclient.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.adapter.TutorialsAdapter
import com.example.mobileclient.databinding.FragmentGuestScreenBinding
import com.example.mobileclient.model.Tutorial
import com.example.mobileclient.viewmodels.TutorialsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GuestScreen : Fragment(),
    TutorialsAdapter.OnItemClickListener {
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
            Tutorial("1", "Tutorial 1", "COURSE", 0.2f),
            Tutorial("2", "Tutorial 2", "FILE_EMERGENCE", 0.5f),
            Tutorial("3", "Tutorial 3", "GUIDE", 0.2f),
            Tutorial("4", "Tutorial 4", "FILE_EMERGENCE", 0.7f),
            Tutorial("5", "Tutorial 5", "COURSE", 0.25f),
        )
        var tutorialsAdapter = TutorialsAdapter(tutorialsEmpty, this, ratingBarChangeListener)
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
        binding.navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            if (it.toString() == getString(R.string.sign_up)) {
                Navigation.findNavController(view).navigate(R.id.action_guestScreen_to_register)
            } else if (it.toString() == getString(R.string.log_in)) {
                Navigation.findNavController(view).navigate(R.id.action_guestScreen_to_login)
            }
            binding.drawerLayout.close()
            true
        }
        return view
    }

    override fun onItemClick(position: Int) {
        Log.d("Tutorial clicked", "Guest clicked tutorial $position")
        context?.let {
            MaterialAlertDialogBuilder(it).setTitle(getString(R.string.log_in_to_see_tutorial))
                .setMessage(getString(R.string.log_in_to_see_tutorial_message))
                .setNegativeButton(getString(R.string.register)) { _, _ ->
                    Navigation.findNavController(view!!)
                        .navigate(R.id.register)
                }
                .setPositiveButton(getString(R.string.log_in)) { _, _ ->
                    Navigation.findNavController(view!!)
                        .navigate(R.id.login)
                }
                .show()
        }
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
}