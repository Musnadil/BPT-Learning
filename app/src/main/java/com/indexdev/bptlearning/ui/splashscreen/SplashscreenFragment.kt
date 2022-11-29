package com.indexdev.bptlearning.ui.splashscreen

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.indexdev.bptlearning.R
import com.indexdev.bptlearning.databinding.FragmentSplashscreenBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.DEFAULT_VALUE
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.SHARED_PREFERENCES
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.LOGIN_PREFERENCES

class SplashscreenFragment : Fragment() {

    private var _binding : FragmentSplashscreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashscreenBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preference = requireContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val login = preference.getString(LOGIN_PREFERENCES, DEFAULT_VALUE)

        Handler(Looper.getMainLooper()).postDelayed({
            if (login == DEFAULT_VALUE){
                findNavController().navigate(R.id.action_splashscreenFragment_to_loginFragment)
            }else{
                findNavController().navigate(R.id.action_splashscreenFragment_to_homeFragment)
            }
        },3000)
    }
}