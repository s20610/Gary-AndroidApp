package com.example.mobileclient.fragments

//import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentParamedicCallForSupportBinding
import com.example.mobileclient.model.Backup
import com.example.mobileclient.viewmodels.ParamedicViewModel

class ParamedicCallForSupport : Fragment() {
    private var _binding: FragmentParamedicCallForSupportBinding? = null
    private val binding get() = _binding!!
    private val paramedicViewModel: ParamedicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParamedicCallForSupportBinding.inflate(inflater, container, false)

        val ambulanceCheck = binding.checkBoxA
        val fireTruckCheck = binding.checkBoxB
        val policeCheck = binding.checkBoxC

        binding.callButton?.setOnClickListener {
            val isAmbulanceChecked = ambulanceCheck.isChecked
            val isFireTruckChecked = fireTruckCheck.isChecked
            val isPoliceChecked = policeCheck.isChecked
            var backupType: Backup.Companion.BackupType? = null
            when {
                isAmbulanceChecked && isFireTruckChecked && isPoliceChecked -> backupType =
                    Backup.Companion.BackupType.POLICE_AMBULANCE_FIRE
                isAmbulanceChecked && isFireTruckChecked -> backupType =
                    Backup.Companion.BackupType.AMBULANCE_FIRE
                isAmbulanceChecked && isPoliceChecked -> backupType =
                    Backup.Companion.BackupType.POLICE_AMBULANCE
                isFireTruckChecked && isPoliceChecked -> backupType =
                    Backup.Companion.BackupType.POLICE_FIRE
                isAmbulanceChecked -> backupType = Backup.Companion.BackupType.AMBULANCE
                isFireTruckChecked -> backupType = Backup.Companion.BackupType.FIRE_FIGHTERS
                isPoliceChecked -> backupType = Backup.Companion.BackupType.POLICE
                else -> Toast.makeText(
                    context,
                    getString(R.string.no_backup_type_selected),
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (backupType != null) {
                val backup = Backup(1,"",1,false,"",backupType)
                paramedicViewModel.callForBackup(backup)
                paramedicViewModel.callForBackupResponse.observe(viewLifecycleOwner) { response ->
                    if(response.isSuccessful){
                        Log.d("CallForBackup", "Call for backup successful")
                    }
                }
            }
        }

        val view = binding.root

        binding.cancelButton?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.paramedicScreen)
        }

        return view
    }
}