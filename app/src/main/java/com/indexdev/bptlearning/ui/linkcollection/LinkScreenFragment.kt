package com.indexdev.bptlearning.ui.linkcollection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.indexdev.bptlearning.R
import com.indexdev.bptlearning.data.model.MateriModel
import com.indexdev.bptlearning.data.model.NewModel
import com.indexdev.bptlearning.databinding.FragmentLinkScreenBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.ENTITY_MAPEL
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.LINK_KEY
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.MAPEL_KEY
import kotlin.time.days


class LinkScreenFragment : Fragment() {
    private var _binding: FragmentLinkScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private lateinit var listSitus: ArrayList<MateriModel?>
    private val bundle = Bundle()

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
        db = Firebase.firestore
        listSitus = arrayListOf()


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        fetchSitus(namaMapel ?: "")
    }

    private fun fetchSitus(namaMapel: String) {
        listSitus.clear()
        db.collection(ENTITY_MAPEL)
            .document(namaMapel)
            .get()
            .addOnSuccessListener {
                binding.pbLoading.visibility = View.GONE
                if (it != null) {
                    val allMateri = it.toObject<NewModel>()
                    for (i in 0 until allMateri?.Materi!!.size){
                        val materi = MateriModel(
                            allMateri.Materi[i].namaMateri,
                            allMateri.Materi[i].keterangan,
                            allMateri.Materi[i].situs,
                        )
                        listSitus.add(materi)
                    }
                    Log.d("asdasd","${listSitus.size}")
                    val linkAdapter = LinkAdapter(listSitus)
                    binding.rvSitus.adapter = linkAdapter
                    bundle.putString(MAPEL_KEY, namaMapel)

                    linkAdapter.setOnItemClickListener(object : LinkAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
//                            bundle.putString(LINK_KEY,listSitus[position])
                            findNavController().navigate(
                                R.id.action_linkScreenFragment_to_webViewFragment,
                                bundle
                            )
                        }
                    })
                }

                if (listSitus.size == 0) {
                    binding.tvTextWarning.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                    binding.rvSitus.visibility = View.GONE
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "null", Toast.LENGTH_SHORT).show()
            }

    }
}