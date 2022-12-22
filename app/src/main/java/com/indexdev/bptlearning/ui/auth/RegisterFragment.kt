package com.indexdev.bptlearning.ui.auth

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Patterns
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
import com.indexdev.bptlearning.databinding.FragmentRegisterBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.ENTITY_USER
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.FIELD_EMAIL
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.USERNAME_KEY

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val bundle = Bundle()
    private lateinit var db: FirebaseFirestore
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.firestore
        binding.btnDaftar.setOnClickListener {
            userCheck()
        }
        binding.btnMasuk.setOnClickListener {
            findNavController().popBackStack()
        }
        progressDialog = ProgressDialog(requireContext())
    }

    private fun userCheck() {
        val email = binding.etEmail.text.toString().lowercase()
        val username = binding.etUsername.text.toString().lowercase()
        val password = binding.etPassword.text.toString()
        val confPassword = binding.etKonfirmasiPassword.text.toString()
        if (email.isEmpty()) {
            binding.emailContainer.error = "Email tidak boleh kosong"
            binding.etEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailContainer.error = "Email tidak valid"
            binding.etEmail.requestFocus()
        } else if (username.isEmpty()) {
            binding.usernameContainer.error = "Nama tidak boleh kosong"
            binding.etUsername.requestFocus()
        } else if (password.isEmpty()) {
            binding.passwordContainer.error = "Password tidak boleh kosong"
            binding.etPassword.requestFocus()
        } else if (confPassword.isEmpty()) {
            binding.konfirmasiPasswordContainer.error = "Konfirmasi Password tidak boleh kosong"
            binding.etKonfirmasiPassword.requestFocus()
        } else if (password != confPassword) {
            binding.konfirmasiPasswordContainer.error = "Konfirmasi Password tidak sama"
            binding.etKonfirmasiPassword.requestFocus()
        } else {
            progressDialog.setMessage("Mohon tunggu sebentar...")
            progressDialog.show()

            val getUsername = db.collection(ENTITY_USER).document(username)
            getUsername.get()
                .addOnSuccessListener {
                    //checking username (primary field)
                    if (it.data != null) {
                        progressDialog.dismiss()
                        //username already taken
                        AlertDialog.Builder(context)
                            .setTitle("Pesan")
                            .setMessage("Username sudah digunakan")
                            .setCancelable(false)
                            .setPositiveButton("OK") { positive, _ ->
                                positive.dismiss()
                                binding.etUsername.requestFocus()
                            }
                            .show()
                    } else {
                        db.collection(ENTITY_USER)
                            .whereEqualTo(FIELD_EMAIL, email)
                            .get()
                            .addOnSuccessListener { emailCheck ->
                                // email check(field)
                                if (emailCheck.documents.isNotEmpty()) {
                                    progressDialog.dismiss()
                                    AlertDialog.Builder(context)
                                        .setTitle("Pesan")
                                        .setMessage("Email sudah digunakan")
                                        .setCancelable(false)
                                        .setPositiveButton("OK") { positive, _ ->
                                            positive.dismiss()
                                            binding.etEmail.requestFocus()
                                        }
                                        .show()
                                } else {
                                    register(email, username, password)
                                }
                            }
                            .addOnFailureListener { emailCheck ->
                                progressDialog.dismiss()
                                Toast.makeText(
                                    requireContext(),
                                    "${emailCheck.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun register(email: String, username: String, password: String) {
        bundle.putString(USERNAME_KEY, username)
        val user = hashMapOf(
            "email" to email,
            "password" to password
        )
        db.collection(ENTITY_USER).document(username)
            .set(user)
            .addOnSuccessListener {
                progressDialog.dismiss()
                AlertDialog.Builder(context)
                    .setTitle("Pesan")
                    .setMessage("Pendaftaran berhasil, mohon masuk kembali")
                    .setCancelable(false)
                    .setPositiveButton("OK") { positive, _ ->
                        positive.dismiss()
                        findNavController().navigate(
                            R.id.action_registerFragment_to_loginFragment,
                            bundle
                        )
                    }
                    .show()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}