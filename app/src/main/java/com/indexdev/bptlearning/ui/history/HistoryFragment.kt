package com.indexdev.bptlearning.ui.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.indexdev.bptlearning.R
import com.indexdev.bptlearning.data.model.HistoryModel
import com.indexdev.bptlearning.databinding.FragmentHistoryBinding
import com.indexdev.bptlearning.ui.ConstantVariable
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.DEFAULT_VALUE
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.LOGIN_PREFERENCES
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.SHARED_PREFERENCES

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbRef: DatabaseReference
    private lateinit var historyList: ArrayList<HistoryModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyList = arrayListOf<HistoryModel>()

        val preference =
            requireContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val username = preference.getString(LOGIN_PREFERENCES, DEFAULT_VALUE).toString()
        dbRef = FirebaseDatabase.getInstance().getReference(username)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                historyList.clear()
                if (snapshot.exists()) {
                    binding.pbLoading.visibility = View.GONE
                    //loading gone
                    for (productSnap in snapshot.children.reversed()) {
                        val dataHistory = productSnap.getValue(HistoryModel::class.java)
                        dataHistory?.let { historyList.add(it) }
                    }
                    for (i in 0 until historyList.size){
                        if (i > 15){
                            dbRef.child(historyList[i].hostoryID?:"").removeValue()
                        }
                    }

                    val historyAdapter = HistoryAdapter(historyList)
                    binding.rvHistory.adapter = historyAdapter
                    historyAdapter.setOnItemClickListener(object :
                        HistoryAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            //
                        }
                    })
                }else{
                    binding.pbLoading.visibility = View.GONE
                    binding.tvTextWarning.visibility = View.VISIBLE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                //
            }

        })

    }
}