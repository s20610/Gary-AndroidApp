package com.example.mobileclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.databinding.ListItemAllergiesBinding
import com.example.mobileclient.model.Allergy

class AllergyAdapter(
    private val allergies: List<Allergy>,
    private var onItemClickListener: OnItemClickListener,
) :
    RecyclerView.Adapter<AllergyAdapter.ItemViewHolder>() {

    class ItemViewHolder(
        private var binding: ListItemAllergiesBinding,
        private var itemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(allergy: Allergy) {
            binding.allergy = allergy
            binding.root.setOnClickListener(this)
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            itemClickListener.onAllergyClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListItemAllergiesBinding.inflate(LayoutInflater.from(parent.context)),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val allergy = allergies[position]
        holder.bind(allergy)
    }

    override fun getItemCount(): Int {
        return allergies.size
    }

    interface OnItemClickListener {
        fun onAllergyClick(position: Int)
    }

    fun getAllergy(position: Int): Allergy {
        return allergies[position]
    }
}