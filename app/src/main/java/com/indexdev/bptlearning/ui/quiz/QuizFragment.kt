package com.indexdev.bptlearning.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import com.indexdev.bptlearning.R
import com.indexdev.bptlearning.data.model.QuizModel
import com.indexdev.bptlearning.databinding.FragmentQuizBinding
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.ENTITY_QUIZ
import com.indexdev.bptlearning.ui.ConstantVariable.Companion.LINK_KEY

class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private val bundle = Bundle()


    private lateinit var dbRef: DatabaseReference
    private lateinit var quizList: ArrayList<QuizModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quizList = arrayListOf()

        dbRef = FirebaseDatabase.getInstance().getReference(ENTITY_QUIZ)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                quizList.clear()
                if (snapshot.exists()) {
                    binding.pbLoading.visibility = View.GONE

                    for (producSnap in snapshot.children) {
                        val dataQuiz = producSnap.getValue(QuizModel::class.java)
                        dataQuiz?.let { quizList.add(it) }
                    }
                    val quizAdapter = QuizAdapter(quizList)
                    binding.rvQuiz.adapter = quizAdapter
                    quizAdapter.setOnItemClickListener(
                        object : QuizAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                //go to web view to open google form
//                                quizList[position].quizLink
                                bundle.putString(LINK_KEY, quizList[position].quizLink)
                                findNavController().navigate(
                                    R.id.action_quizFragment_to_quizWebViewFragment,
                                    bundle
                                )
                            }
                        })
                } else {
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