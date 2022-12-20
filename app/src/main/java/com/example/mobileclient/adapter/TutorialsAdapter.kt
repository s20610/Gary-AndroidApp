package com.example.mobileclient.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.example.mobileclient.R
import com.example.mobileclient.databinding.TutorialViewItemBinding
import com.example.mobileclient.model.Tutorial

class TutorialsAdapter(
    private val context: Context,
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
        fun bind(tutorial: Tutorial, context: Context) {
            binding.tutorial = tutorial
            setPicture(context, tutorial.thumbnail)
            binding.ratingBar.onRatingBarChangeListener = onRatingBarChangeListener
            binding.root.setOnClickListener(this)
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            itemClickListener.onItemClick(adapterPosition)
        }

        private fun setPicture(context: Context, url: String) {
            Glide.with(context).load(Uri.parse(url)).override(200,200).placeholder(R.drawable.ic_placeholder).centerCrop().into(binding.thumbnail)
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
        holder.bind(tutorial, context)
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
