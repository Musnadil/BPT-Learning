package com.indexdev.bptlearning.ui.linkcollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.indexdev.bptlearning.R
import com.indexdev.bptlearning.databinding.FragmentLinkScreenBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.MAPEL_KEY

class LinkScreenFragment : Fragment() {
    private var _binding: FragmentLinkScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLinkScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val namaMapel = arguments?.getString(MAPEL_KEY)
        binding.tvMapel.text = namaMapel

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}