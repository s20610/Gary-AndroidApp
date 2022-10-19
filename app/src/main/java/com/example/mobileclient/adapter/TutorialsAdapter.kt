package com.example.mobileclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.databinding.TutorialViewItemBinding
import com.example.mobileclient.model.Tutorial

class TutorialsAdapter(
    private var tutorials: List<Tutorial>,
    private var onItemClickListener: OnItemClickListener,
    private var onRatingBarChangeListener: RatingBar.OnRatingBarChangeListener
) :
    RecyclerView.Adapter<TutorialsAdapter.TutorialViewHolder>() {

    class TutorialViewHolder(
        private var binding: TutorialViewItemBinding,
        private var itemClickListener: OnItemClickListener,
        private var onRatingBarChangeListener: RatingBar.OnRatingBarChangeListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(tutorial: Tutorial) {
            binding.tutorial = tutorial
            binding.ratingBar.onRatingBarChangeListener = onRatingBarChangeListener
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
            onItemClickListener, onRatingBarChangeListener
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
