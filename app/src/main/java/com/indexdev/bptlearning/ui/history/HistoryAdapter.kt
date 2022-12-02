package com.indexdev.bptlearning.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indexdev.bptlearning.data.model.HistoryModel
import com.indexdev.bptlearning.databinding.ItemHistoryBinding

class HistoryAdapter(private val historyList: ArrayList<HistoryModel>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(
        val binding: ItemHistoryBinding,
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
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvNamaSitus.text = historyList[position].nama_situs
            tvLink.text = historyList[position].link
        }
    }

    override fun getItemCount() = historyList.size


}