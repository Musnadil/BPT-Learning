package com.indexdev.bptlearning.ui.linkcollection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indexdev.bptlearning.databinding.ItemSitusBinding

class LinkAdapter(private var listSitus: ArrayList<String>) :
    RecyclerView.Adapter<LinkAdapter.ViewHolder>() {

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

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSitusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvNamaSitus.text = listSitus[position]
        }
    }

    override fun getItemCount() = listSitus.size

}