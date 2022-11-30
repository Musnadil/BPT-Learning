package com.indexdev.bptlearning.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indexdev.bptlearning.databinding.ItemMapelBinding

class MapelAdapter(private val mapelList: ArrayList<String>) :
    RecyclerView.Adapter<MapelAdapter.ViewHolder>() {
    class ViewHolder(
        val binding: ItemMapelBinding,
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

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMapelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvMapel.text = mapelList[position]
        }
    }

    override fun getItemCount() = mapelList.size
}