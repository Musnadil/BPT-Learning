package com.indexdev.bptlearning.ui.profil

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.indexdev.bptlearning.databinding.FragmentProfilBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.DEFAULT_VALUE
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.EMAIL_PREFERENCES
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.SHARED_PREFERENCES
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.LOGIN_PREFERENCES

class ProfilFragment : Fragment() {
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preference = requireContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val email = preference.getString(EMAIL_PREFERENCES, DEFAULT_VALUE)
        val username = preference.getString(LOGIN_PREFERENCES, DEFAULT_VALUE)
        binding.tvUsername.text = username
        binding.tvEmail.text = email
    }
}