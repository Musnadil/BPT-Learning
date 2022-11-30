package com.indexdev.bptlearning.ui.additem

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.indexdev.bptlearning.R
import com.indexdev.bptlearning.databinding.FragmentAddItemDialogBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.ENTITY_MAPEL

class AddItemDialogFragment() : DialogFragment() {
    private var _binding: FragmentAddItemDialogBinding? = null
    private val binding get() = _binding!!
    lateinit var selectedMode: String
    private lateinit var db: FirebaseFirestore
    val listMapel: MutableList<String> = ArrayList()

    constructor(choice: String) : this() {
        this.selectedMode = choice
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        dialog?.window?.attributes?.windowAnimations = R.style.MyDialogAnimation
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        _binding = FragmentAddItemDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.firestore

        var mode = ""
        if (this::selectedMode.isInitialized) {
            mode = selectedMode
        }

        if (mode == "mapel") {
            mapelMode()
        } else if (mode == "situs") {
            situsMode()
        }
    }

    private fun mapelMode() {
        binding.etDropdownContainer.visibility = View.GONE
        binding.etContainerNamaSitus.visibility = View.GONE
        binding.etContainer.hint = "Mata Pelajaran"
        binding.tvTitle.text = "Tambah Mata Pelajaran"
        binding.btnTambah.setOnClickListener {
            binding.etContainer.error = null
            binding.btnTambah.isEnabled = false
            val mapelName = binding.etEditText.text.toString()
            if (mapelName.isEmpty()) {
                binding.etContainer.error = "Mata pelajaran tidak boleh kosong"
                binding.btnTambah.isEnabled = true
            } else {
                val getMapel = db.collection(ENTITY_MAPEL).document(mapelName)
                getMapel.get()
                    .addOnSuccessListener {
                        binding.btnTambah.isEnabled = true
                        if (it.data != null) {
                            AlertDialog.Builder(context)
                                .setTitle("Pesan")
                                .setMessage("Mata pelajaran $mapelName sudah ada")
                                .setCancelable(false)
                                .setPositiveButton("OK") { positive, _ ->
                                    positive.dismiss()
                                }
                                .show()
                        } else {
                            val mapel = HashMap<String, Any>()
                            db.collection(ENTITY_MAPEL).document(mapelName)
                                .set(mapel)
                                .addOnSuccessListener {
                                    binding.btnTambah.isEnabled = true
                                    Toast.makeText(
                                        requireContext(),
                                        "Berhasil menambahkan mata pelajaran",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    dialog?.dismiss()
                                }
                                .addOnFailureListener {
                                    binding.btnTambah.isEnabled = true
                                    Toast.makeText(
                                        requireContext(),
                                        "${it.message}",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                        }
                    }
                    .addOnFailureListener {
                        binding.btnTambah.isEnabled = true
                        Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun situsMode() {
        listMapel.clear()
        binding.btnTambah.isEnabled = false
        binding.etDropdownContainer.visibility = View.GONE
        val getMapel = db.collection(ENTITY_MAPEL)
        getMapel.get()
            .addOnSuccessListener {
                binding.btnTambah.isEnabled = true
                binding.etDropdownContainer.visibility = View.VISIBLE
                for (document in it) {
                    listMapel.add(document.id)
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            }
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, listMapel)
        binding.etDropdownText.setAdapter(arrayAdapter)

        binding.btnTambah.setOnClickListener {
            binding.btnTambah.isEnabled = false
            val mapel = binding.etDropdownText.text.toString()
            val namaSitus = binding.etNamaSitus.text.toString()
            val situs = binding.etEditText.text.toString()
            val data = hashMapOf(namaSitus to situs)
            binding.etDropdownContainer.error = null
            binding.etContainerNamaSitus.error = null
            binding.etContainer.error = null
            if (mapel.isEmpty()) {
                binding.etDropdownContainer.error = "Anda harus pilih salah satu mata pelajaran"
                binding.btnTambah.isEnabled = true
            } else if (namaSitus.isEmpty()) {
                binding.etContainerNamaSitus.error = "Nama situs tidak boleh kosong"
                binding.btnTambah.isEnabled = true
            } else if (situs.isEmpty()) {
                binding.etContainer.error = "Link tidak boleh kosong"
                binding.btnTambah.isEnabled = true
            } else {
                db.collection(ENTITY_MAPEL).document(mapel)
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener {
                        binding.btnTambah.isEnabled = true
                        Toast.makeText(
                            requireContext(),
                            "Berhasil menambahkan link $namaSitus",
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog?.dismiss()
                    }
                    .addOnFailureListener {
                        binding.btnTambah.isEnabled = true
                        Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}