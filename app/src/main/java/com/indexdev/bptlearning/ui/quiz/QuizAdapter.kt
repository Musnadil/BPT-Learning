package com.indexdev.bptlearning.ui.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indexdev.bptlearning.data.model.QuizModel
import com.indexdev.bptlearning.databinding.ItemSitusBinding

class QuizAdapter(private val quizList: ArrayList<QuizModel>) :
    RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(
        val binding: ItemSitusBinding,
        private val clickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    private lateinit var mListener: OnItemClickListener

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSitusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvNomor.text = (position + 1).toString()
            tvNamaSitus.text = quizList[position].quizName
        }
    }

    override fun getItemCount() = quizList.size


}