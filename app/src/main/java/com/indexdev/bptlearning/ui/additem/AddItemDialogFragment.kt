package com.indexdev.bptlearning.ui.additem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.indexdev.bptlearning.R
import com.indexdev.bptlearning.databinding.FragmentAddItemDialogBinding

class AddItemDialogFragment : DialogFragment() {
    private var _binding: FragmentAddItemDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemDialogBinding.inflate(inflater, container, false)
        val mapel = resources.getStringArray(R.array.mapel)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,mapel)
        binding.etDropdownText.setAdapter(arrayAdapter)
        return binding.root
    }

}