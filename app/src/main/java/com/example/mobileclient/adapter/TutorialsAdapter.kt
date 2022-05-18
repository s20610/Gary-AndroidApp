package com.example.mobileclient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.databinding.TutorialViewItemBinding
import com.example.mobileclient.model.Tutorial

class TutorialsAdapter(private val tutorials: List<Tutorial>): RecyclerView.Adapter<TutorialsAdapter.TutorialViewHolder>() {


    class TutorialViewHolder(
        private var binding: TutorialViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tutorial: Tutorial) {
            binding.tutorial = tutorial
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialViewHolder {
        return TutorialViewHolder(
            TutorialViewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: TutorialViewHolder, position: Int) {
        val tutorial = tutorials[position]
        holder.bind(tutorial)
    }

    override fun getItemCount(): Int {
        return tutorials.size
    }
}