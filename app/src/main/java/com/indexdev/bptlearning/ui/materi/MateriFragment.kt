package com.indexdev.bptlearning.ui.materi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.indexdev.bptlearning.R
import com.indexdev.bptlearning.databinding.FragmentMateriBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.KETERANGAN
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.LINK_KEY
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.NAMA_MATERI

class MateriFragment : Fragment() {
    private var _binding: FragmentMateriBinding? = null
    private val binding get() = _binding!!
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMateriBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val namaMateri = arguments?.getString(NAMA_MATERI)
        val keterangan = arguments?.getString(KETERANGAN)
        val link = arguments?.getString(LINK_KEY)
        bundle.putString(LINK_KEY, link)
        bundle.putString(NAMA_MATERI, namaMateri)

        binding.tvMateriTitle.text = namaMateri
        binding.tvTextKeterangan.text = keterangan

        binding.btnWebView.setOnClickListener {
            findNavController().navigate(R.id.action_materiFragment_to_webViewFragment, bundle)
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}