package com.example.mobileclient.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentUserInfoBinding
import com.example.mobileclient.model.passwordChange
import com.example.mobileclient.util.Constants.Companion.USER_INFO_PREFS
import com.example.mobileclient.util.Constants.Companion.USER_TOKEN_TO_PREFS
import com.example.mobileclient.viewmodels.UserViewModel

class UserInfo : Fragment() {
    private var _binding: FragmentUserInfoBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        val token = requireActivity().getSharedPreferences(USER_INFO_PREFS, Context.MODE_PRIVATE)
            .getString(USER_TOKEN_TO_PREFS, "")
        binding.saveChangesButton.setOnClickListener {
            if (binding.newPasswordInput.text.toString() == binding.newPasswordConfirmInput.text.toString()) {
                binding.newPasswordInput.error = null
                binding.newPasswordConfirmInput.error = null
                val passwordChange = passwordChange(
                    binding.oldPasswordInput.text.toString(),
                    binding.newPasswordInput.text.toString()
                )
                if (token != null) {
                    userViewModel.changePassword(token, passwordChange)
                }
                userViewModel.changePasswordResponse.observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        Navigation.findNavController(view)
                            .navigate(R.id.action_userInfo_to_loggedInScreen)
                    }
                }
            } else {
                binding.newPasswordInput.error = getString(R.string.password_not_matching)
                binding.newPasswordConfirmInput.error = getString(R.string.password_not_matching)
            }
        }

        return view
    }
}