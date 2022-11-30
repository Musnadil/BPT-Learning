package com.indexdev.bptlearning.ui.webview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.indexdev.bptlearning.R
import com.indexdev.bptlearning.databinding.FragmentWebViewBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.ENTITY_MAPEL
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.LINK_KEY
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.MAPEL_KEY

class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var webView: WebView
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val statusBarHeight = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (statusBarHeight > 0) {
            binding.statusbar.layoutParams.height = resources.getDimensionPixelSize(statusBarHeight)
        }
        var link = ""
        db = Firebase.firestore
        val mapel = arguments?.getString(MAPEL_KEY).toString()
        val situs = arguments?.getString(LINK_KEY).toString()
        binding.tvSitus.text = situs
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        db.collection(ENTITY_MAPEL).document(mapel)
            .get()
            .addOnSuccessListener {
                if (it.data != null) {
                    link = it.get(situs).toString()
                }
                webView = binding.wvDetailNews
                webView.webViewClient = WebViewClient()
                webView.loadUrl(link)
                webView.canGoBack()
                val webSettings: WebSettings = webView.settings
                webSettings.javaScriptEnabled = true
                webSettings.domStorageEnabled = true
                webView.webChromeClient = object :WebChromeClient(){
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        binding.progressbar.progress = newProgress
                    }
                }
                webView.webViewClient = object :WebViewClient(){
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        binding.progressbar.visibility = View.GONE
                    }
                }
            }
    }
}