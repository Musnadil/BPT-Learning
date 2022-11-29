package com.indexdev.bptlearning.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.indexdev.bptlearning.databinding.FragmentHomeBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.DEFAULT_VALUE
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.SHARED_PREFERENCES
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.LOGIN_PREFERENCES

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val statusBarHeight = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (statusBarHeight > 0) {
            binding.statusbar.layoutParams.height = resources.getDimensionPixelSize(statusBarHeight)
        }
        val preference = requireContext().getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE)
        val username = preference.getString(LOGIN_PREFERENCES, DEFAULT_VALUE)
        binding.tvUsername.text = username
    }
}