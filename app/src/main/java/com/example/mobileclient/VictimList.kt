package com.example.mobileclient

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.adapter.VictimInfoAdapter
import com.example.mobileclient.databinding.FragmentVictimListBinding
import com.example.mobileclient.util.Constants
import com.example.mobileclient.viewmodels.ParamedicViewModel


class VictimList : Fragment(), VictimInfoAdapter.OnItemClickListener {
    private var _binding: FragmentVictimListBinding? = null
    private val binding get() = _binding!!
    private val paramedicViewModel: ParamedicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVictimListBinding.inflate(inflater, container, false)
        val view = binding.root
        var incidentId = 0
        val token = requireActivity().getSharedPreferences(
            Constants.USER_INFO_PREFS, Context.MODE_PRIVATE
        ).getString(Constants.USER_TOKEN_TO_PREFS, "")
        paramedicViewModel.assignedIncidentResponse.observe(viewLifecycleOwner) {response ->
            if (response.code() == 200) {
                incidentId = response.body()?.incidentId!!
            }
        }
        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.addVictimInfo)
        }
        binding.swipeRefresh.setOnRefreshListener {
            if (incidentId != 0) {
                paramedicViewModel.getCasualties(incidentId, token!!)
                binding.victimInfoRecyclerView.adapter?.notifyDataSetChanged()
            }
            binding.swipeRefresh.isRefreshing = false
        }
        paramedicViewModel.casualtiesResponse.value?.body()?.let {
            binding.victimInfoRecyclerView.adapter = VictimInfoAdapter(it, this)
        }
        return view
    }

    override fun onItemClick(position: Int) {
        val adapter = binding.victimInfoRecyclerView.adapter as VictimInfoAdapter
        val casualty = adapter.getCasualties(position)
        paramedicViewModel.pickedVictimInfo = casualty
        Navigation.findNavController(binding.root).navigate(R.id.action_victimList_to_victimDetails)
        Log.d("Casualty", casualty.toString())
    }
}