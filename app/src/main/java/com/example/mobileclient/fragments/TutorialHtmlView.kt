package com.example.mobileclient.fragments

import android.content.res.AssetManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentLoggedInScreenBinding
import com.example.mobileclient.databinding.FragmentTutorialHtmlViewBinding
import com.example.mobileclient.viewmodels.TutorialsViewModel
import java.io.File

class TutorialHtmlView : Fragment() {
    private var _binding: FragmentTutorialHtmlViewBinding? = null
    private val tutorialsViewModel: TutorialsViewModel by activityViewModels()


    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTutorialHtmlViewBinding.inflate(inflater, container, false)
        val view = binding.root
        if (WebViewFeature.isFeatureSupported(WebViewFeature.ALGORITHMIC_DARKENING)) {
            WebSettingsCompat.setAlgorithmicDarkeningAllowed(binding.tutorialWebView.settings, true)
        }
        val cssFile = requireContext().assets.open("style.css")
        val cssString = cssFile.bufferedReader().use { it.readText() }
        val htmlString = tutorialsViewModel.pickedTutorial!!.tutorialHTML
        val html = "<html><head><style>$cssString</style></head><body>$htmlString</body></html>"
        binding.tutorialWebView.loadData(
            html,
            "text/html",
            "UTF-8"
        )
        return view
    }
}