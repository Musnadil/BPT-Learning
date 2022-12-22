package com.indexdev.bptlearning.ui.profil

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.indexdev.bptlearning.MainActivity
import com.indexdev.bptlearning.R
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
        val preference =
            requireContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val email = preference.getString(EMAIL_PREFERENCES, DEFAULT_VALUE)
        val username = preference.getString(LOGIN_PREFERENCES, DEFAULT_VALUE)
        binding.tvUsername.text = username
        binding.tvEmail.text = email

        binding.btnLogout.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.apply {
                setTitle("Logout")
                setMessage("Anda yakin mau keluar?")
                setNegativeButton("cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                setPositiveButton("Yes") { dialogY, _ ->
                    dialogY.dismiss()
                    preference.edit().clear().apply()
                    activity?.finish()
                    startActivity(Intent(requireContext(),MainActivity::class.java))
//                    findNavController().navigate(R.id.action_profilFragment_to_loginFragment)
                }
            }
            alertDialog.show()
        }
    }
}