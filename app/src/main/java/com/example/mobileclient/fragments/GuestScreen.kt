package com.example.mobileclient.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.Spinner
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GuestScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuestScreen : Fragment(), AdapterView.OnItemSelectedListener,
    TutorialsAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentGuestScreenBinding? = null
    private val tutorialsViewModel: TutorialsViewModel by activityViewModels()
    private var tutorialsFromAPI: List<Tutorial>? = null
    private var currentlyDisplayedTutorials: List<Tutorial>? = null
    private var tutorialsAdapter: TutorialsAdapter = TutorialsAdapter(emptyList(), this)

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
        _binding = FragmentGuestScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val tutorialsEmpty: List<Tutorial> = mutableListOf(
            Tutorial("1", "Tutorial 1", "COURSE", 5.0f),
            Tutorial("2", "Tutorial 2", "FILE_EMERGENCE", 5.0f),
            Tutorial("3", "Tutorial 3", "GUIDE", 5.0f),
            Tutorial("4", "Tutorial 4", "FILE_EMERGENCE", 5.0f),
            Tutorial("5", "Tutorial 5", "COURSE", 5.0f),
        )
        tutorialsAdapter.setTutorials(tutorialsEmpty)
        binding.tutorialsGrid.adapter = tutorialsAdapter
        tutorialsViewModel.getTutorials()
        tutorialsViewModel.getTutorialsResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                tutorialsFromAPI = response.body()
                currentlyDisplayedTutorials = tutorialsFromAPI
                Log.d("getTutorialsResponseBody", response.body().toString())
                binding.tutorialsGrid.adapter =
                    currentlyDisplayedTutorials?.let { TutorialsAdapter(it,this) }
            } else {
                Log.d("getTutorialsResponseBody", response.body().toString())
                Log.d("getTutorialsResponseCode", response.code().toString())
            }
        }
        binding.refresh.setOnRefreshListener {
            tutorialsViewModel.getTutorials()
            tutorialsViewModel.getTutorialsResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    tutorialsFromAPI = response.body()
                    currentlyDisplayedTutorials = tutorialsFromAPI
                    Log.d("getTutorialsResponseBody", response.body().toString())
                    binding.tutorialsGrid.adapter =
                        currentlyDisplayedTutorials?.let { TutorialsAdapter(it,this) }
                    binding.refresh.isRefreshing = false
                } else {
                    Log.d("getTutorialsResponseBody", response.body().toString())
                    Log.d("getTutorialsResponseCode", response.code().toString())
                }
            }
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GuestScreen.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuestScreen().apply {
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
                currentlyDisplayedTutorials?.let { tutorialsAdapter.setTutorials(it) }
                binding.tutorialsGrid.adapter = tutorialsAdapter
            }
            "FILE_EMERGENCE" -> {
                val filteredEmergenceTutorials =
                    tutorialsFromAPI?.filter { it.tutorialKind == "FILE_EMERGENCE" }
                currentlyDisplayedTutorials = filteredEmergenceTutorials
                currentlyDisplayedTutorials?.let { tutorialsAdapter.setTutorials(it) }
                binding.tutorialsGrid.adapter = tutorialsAdapter
            }
            "COURSE" -> {
                val filteredCourseTutorials =
                    tutorialsFromAPI?.filter { it.tutorialKind == "COURSE" }
                currentlyDisplayedTutorials = filteredCourseTutorials
                currentlyDisplayedTutorials?.let { tutorialsAdapter.setTutorials(it) }
                binding.tutorialsGrid.adapter = tutorialsAdapter
            }
            "GUIDE" -> {
                val filteredGuideTutorials =
                    tutorialsFromAPI?.filter { it.tutorialKind == "GUIDE" }
                currentlyDisplayedTutorials = filteredGuideTutorials
                currentlyDisplayedTutorials?.let { tutorialsAdapter.setTutorials(it) }
                binding.tutorialsGrid.adapter = tutorialsAdapter
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        currentlyDisplayedTutorials = tutorialsFromAPI
        currentlyDisplayedTutorials?.let { tutorialsAdapter.setTutorials(it) }
        binding.tutorialsGrid.adapter = tutorialsAdapter
    }

    override fun onItemClick(position: Int) {
        Log.d("Tutorial clicked", "Guest clicked product $position")
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
}