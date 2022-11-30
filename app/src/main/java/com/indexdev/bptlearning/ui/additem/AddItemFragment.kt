package com.indexdev.bptlearning.ui.additem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.indexdev.bptlearning.R
import com.indexdev.bptlearning.databinding.FragmentAddItemBinding

class AddItemFragment : Fragment() {
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        _binding = FragmentAddItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAddMapel.setOnClickListener {
            val dialogAdd = AddItemDialogFragment("mapel")
            activity?.let { dialogAdd.show(it.supportFragmentManager, null) }
        }
        binding.btnAddSitus.setOnClickListener {
            val dialogAdd = AddItemDialogFragment("situs")
            activity?.let { dialogAdd.show(it.supportFragmentManager, null) }
        }
    }
}