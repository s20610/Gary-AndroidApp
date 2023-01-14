package com.example.mobileclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.databinding.ListItemDiseasesBinding
import com.example.mobileclient.model.Disease

class ChronicDiseasesAdapter(
    private var dataset: List<Disease>,
    private var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ChronicDiseasesAdapter.ItemViewHolder>() {

    class ItemViewHolder(
        private var binding: ListItemDiseasesBinding,
        private var itemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(disease: Disease) {
            binding.disease = disease
            binding.root.setOnClickListener(this)
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            itemClickListener.onDiseaseClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListItemDiseasesBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val disease = dataset[position]
        holder.bind(disease)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface OnItemClickListener {
        fun onDiseaseClick(position: Int)
    }

    fun getDisease(position: Int): Disease {
        return dataset[position]
    }

    fun setDiseases(diseases: List<Disease>){
        dataset = diseases
    }
}
