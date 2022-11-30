package com.indexdev.bptlearning.ui.home

import android.content.Context
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
import com.indexdev.bptlearning.databinding.FragmentHomeBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.DEFAULT_VALUE
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.ENTITY_MAPEL
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.LOGIN_PREFERENCES
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.MAPEL_KEY
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.SHARED_PREFERENCES

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private lateinit var listMapel: ArrayList<String>
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val statusBarHeight = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (statusBarHeight > 0) {
            binding.statusbar.layoutParams.height = resources.getDimensionPixelSize(statusBarHeight)
        }
        val preference =
            requireContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val username = preference.getString(LOGIN_PREFERENCES, DEFAULT_VALUE)
        db = Firebase.firestore
        listMapel = arrayListOf()

        binding.tvUsername.text = username

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addItemFragment)
        }
        listMapel.clear()
        val getMapel = db.collection(ENTITY_MAPEL)
        getMapel.get()
            .addOnSuccessListener {
                binding.pbLoading.visibility = View.GONE
                for (document in it) {
                    listMapel.add(document.id)
                    val mapelAdapter = MapelAdapter(listMapel)
                    binding.rvMapel.adapter = mapelAdapter
                    mapelAdapter.setOnItemClickListener(object : MapelAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            bundle.putString(MAPEL_KEY, listMapel[position])
                            findNavController().navigate(
                                R.id.action_homeFragment_to_linkScreenFragment,
                                bundle
                            )
                        }
                    })
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

}