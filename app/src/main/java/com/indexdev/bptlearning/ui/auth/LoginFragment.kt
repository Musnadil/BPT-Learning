package com.indexdev.bptlearning.ui.auth

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.indexdev.bptlearning.R
import com.indexdev.bptlearning.databinding.FragmentLoginBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.ENTITY_USER
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.FIELD_PASSWORD
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.USERNAME_KEY

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.firestore
        binding.btnDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        val bundleUsername = arguments?.getString(USERNAME_KEY)
        binding.etUsername.setText(bundleUsername)

        binding.btnMasuk.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            if (username.isEmpty()) {
                binding.usernameContainer.error = "Nama tidak boleh kosong"
                binding.etUsername.requestFocus()
            } else if (password.isEmpty()) {
                binding.passwordContainer.error = "Password tidak boleh kosong"
                binding.etPassword.requestFocus()
            } else {
                login(username, password)
            }
        }
    }

    private fun login(username: String, password: String) {
        val getUser = db.collection(ENTITY_USER).document(username)
        getUser.get()
            .addOnSuccessListener {
                if (it.data != null) {
                    if (it.get(FIELD_PASSWORD) != password) {
                        AlertDialog.Builder(context)
                            .setTitle("Pesan")
                            .setMessage("Password salah, mohon periksa kembali")
                            .setCancelable(false)
                            .setPositiveButton("OK") { positive, _ ->
                                positive.dismiss()
                                binding.etPassword.requestFocus()
                            }
                            .show()
                    } else {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                } else {
                    AlertDialog.Builder(context)
                        .setTitle("Pesan")
                        .setMessage("Akun belum terdaftar")
                        .setCancelable(false)
                        .setPositiveButton("OK") { positive, _ ->
                            positive.dismiss()
                            binding.etUsername.requestFocus()
                        }
                        .show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}