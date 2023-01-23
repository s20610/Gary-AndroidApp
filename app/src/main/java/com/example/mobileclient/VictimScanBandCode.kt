package com.example.mobileclient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.mobileclient.activities.ScanBandCodeActivity
import com.example.mobileclient.adapter.AllergyAdapter
import com.example.mobileclient.adapter.ChronicDiseasesAdapter
import com.example.mobileclient.databinding.FragmentIncidentBinding
import com.example.mobileclient.databinding.FragmentVictimScanBandCodeBinding
import com.example.mobileclient.model.Allergy
import com.example.mobileclient.model.Disease
import com.example.mobileclient.viewmodels.ParamedicViewModel
import com.example.mobileclient.viewmodels.TypesViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class VictimScanBandCode : Fragment(), AllergyAdapter.OnItemClickListener,
    ChronicDiseasesAdapter.OnItemClickListener {
    private var _binding: FragmentVictimScanBandCodeBinding? = null
    private val binding get() = _binding!!
    private val paramedicViewModel: ParamedicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVictimScanBandCodeBinding.inflate(inflater, container, false)
        val view = binding.root

        val allergiesEmpty: List<Allergy> = mutableListOf(
            Allergy("", "", "", ""),
        )
        val chronicDiseasesEmpty: List<Disease> = mutableListOf(
            Disease("", "", "", true),
        )
        binding.allergyView.adapter = AllergyAdapter(allergiesEmpty, this)
        binding.diseaseView.adapter = ChronicDiseasesAdapter(chronicDiseasesEmpty, this)
        val barcodeLauncher = registerForActivityResult(
            ScanContract()
        ) { result: ScanIntentResult ->
            if (result.contents == null) {
                Toast.makeText(
                    context,
                    resources.getString(R.string.barcode_scanning_cancelled),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "${resources.getString(R.string.barcode_scanning_successful)} ${result.contents}",
                    Toast.LENGTH_LONG
                ).show()
                binding.barcodeInputText.setText(result.contents)
                paramedicViewModel.getMedicalInfoWithBandCode(result.contents)
                paramedicViewModel.victimMedicalInfoResponse.observe(viewLifecycleOwner) { response ->
                    Log.d("Medical Info", response.body().toString())
                    if (response.isSuccessful) {
                        val bloodType = response.body()?.bloodType.toString()
                        val rhType = response.body()?.rhType.toString()
                        val fullBlood = "$bloodType $rhType"
                        binding.bloodTypeText.text = fullBlood
                        val allergiesFromApi: List<Allergy> = response.body()!!.allergies
                        val chronicDiseasesFromApi: List<Disease> = response.body()!!.diseases
                        val allergyAdapter = binding.allergyView.adapter as AllergyAdapter
                        val diseaseAdapter = binding.diseaseView.adapter as ChronicDiseasesAdapter
                        allergyAdapter.setAllergies(allergiesFromApi)
                        diseaseAdapter.setDiseases(chronicDiseasesFromApi)
                        allergyAdapter.notifyDataSetChanged()
                        diseaseAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        val options = ScanOptions()
        options.setPrompt(resources.getString(R.string.scan_prompt))
        options.setCameraId(0)
        options.setBeepEnabled(true)
        options.captureActivity = ScanBandCodeActivity::class.java
        options.setOrientationLocked(false)
        options.setBarcodeImageEnabled(true)
        binding.scanButton.setOnClickListener {
            barcodeLauncher.launch(options)
        }

        return view
    }

    override fun onAllergyClick(position: Int) {

    }

    override fun onDiseaseClick(position: Int) {

    }
}