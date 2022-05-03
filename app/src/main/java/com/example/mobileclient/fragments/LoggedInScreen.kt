package com.example.mobileclient.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentLoggedInScreenBinding
import com.example.mobileclient.model.UserViewModel
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
class LoggedInScreen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentLoggedInScreenBinding? = null
    private val sharedViewModel: UserViewModel by activityViewModels()

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
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoggedInScreenBinding.inflate(inflater, container, false)


        //val view1 = inflater.inflate(R.layout.user_navigation_drawer_header, null) as View
        //view1.findViewById<TextView>(R.id.email_field_text).text = email

        val view = binding.root

        val intent = Intent()
        val email = intent.getStringExtra("E-mail")
        Toast.makeText(context, "Email zalogowano: "+email, Toast.LENGTH_LONG)
        binding.guestScreenText.text = email
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
            binding.navigationView.setCheckedItem(R.id.nav_tutorials)
        }
        binding.navigationView.getHeaderView(0).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loggedInScreen_to_userInfo)

        }
        binding.addIncidentButton.setOnClickListener{
            context?.let { it1 -> MaterialAlertDialogBuilder(it1).setTitle("Create Incident?")
                .setMessage("Unjustified ambulance call may result in a fine. Are you sure?")
                .setNegativeButton("Cancel"){
                    dialog, which -> dialog.cancel()
                }
                .setPositiveButton("Accept"){
                    dialog, which -> Navigation.findNavController(view).navigate(R.id.action_loggedInScreen_to_incident)
                }
                .show()}
        }

        binding.browseButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loggedInScreen_to_incidentsBrowse2)
        }

        binding.navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            if (it.toString() == "Log out") {
                Navigation.findNavController(view)
                    .navigate(R.id.action_loggedInScreen_to_splashScreen)
            }else if(it.toString() == "User Details"){
                Navigation.findNavController(view).navigate((R.id.action_loggedInScreen_to_medicalInfoMain))
            }
            binding.drawerLayout.close()
            true
        }
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
}