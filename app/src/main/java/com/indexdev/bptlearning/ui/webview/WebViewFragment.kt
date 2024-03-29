package com.indexdev.bptlearning.ui.webview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.indexdev.bptlearning.data.model.HistoryModel
import com.indexdev.bptlearning.databinding.FragmentWebViewBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.DEFAULT_VALUE
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.LINK_KEY
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.LOGIN_PREFERENCES
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.NAMA_MATERI
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.SHARED_PREFERENCES

class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var webView: WebView
    private lateinit var db: FirebaseFirestore
    private lateinit var dbRef: DatabaseReference


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
        val preference =
            requireContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val username = preference.getString(LOGIN_PREFERENCES, DEFAULT_VALUE).toString()
        dbRef = FirebaseDatabase.getInstance().getReference(username)

//        var link = ""
//        db = Firebase.firestore

        val namaMateri = arguments?.getString(NAMA_MATERI).toString()
        val url = arguments?.getString(LINK_KEY).toString()
        binding.tvSitus.text = namaMateri

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        webView = binding.wvDetailNews
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
        webView.canGoBack()
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.progressbar.progress = newProgress
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressbar.visibility = View.GONE
            }
        }
        val historyID = dbRef.push().key!!
        val history = HistoryModel(historyID, namaMateri, url)

        dbRef.child(historyID).setValue(history)

    }
}