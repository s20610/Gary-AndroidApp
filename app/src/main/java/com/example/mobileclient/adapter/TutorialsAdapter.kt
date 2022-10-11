package com.example.mobileclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.databinding.TutorialViewItemBinding
import com.example.mobileclient.model.Tutorial

class TutorialsAdapter(
    private var tutorials: List<Tutorial>,
    private var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TutorialsAdapter.TutorialViewHolder>() {

    class TutorialViewHolder(
        private var binding: TutorialViewItemBinding,
        private var itemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(tutorial: Tutorial) {
            binding.tutorial = tutorial
            binding.root.setOnClickListener(this)
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            itemClickListener.onItemClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialViewHolder {
        return TutorialViewHolder(
            TutorialViewItemBinding.inflate(LayoutInflater.from(parent.context)),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: TutorialViewHolder, position: Int) {
        val tutorial = tutorials[position]
        holder.bind(tutorial)
    }

    override fun getItemCount(): Int {
        return tutorials.size
    }

    fun setTutorials(tutorials: List<Tutorial>) {
        this.tutorials = tutorials
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}