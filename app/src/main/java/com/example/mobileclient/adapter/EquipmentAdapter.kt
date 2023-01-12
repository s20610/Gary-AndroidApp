package com.example.mobileclient.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.databinding.EquipmentItemBinding
import com.example.mobileclient.model.AmbulanceEquipment

class EquipmentAdapter(
    private var items: List<AmbulanceEquipment>,
    private var onItemClickListener: OnItemClickListener,
) :
    RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {

    class EquipmentViewHolder(
        private var binding: EquipmentItemBinding,
        private var itemClickListener: OnItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: AmbulanceEquipment) {
            binding.equipment = item
            binding.count.text = item.itemData.count.toString()
            binding.plus.setOnClickListener { itemClickListener.onPlusClick(adapterPosition) }
            binding.minus.setOnClickListener { itemClickListener.onMinusClick(adapterPosition) }
            Log.d("EquipmentAdapter", item.toString())
            binding.root.setOnClickListener(this)
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            itemClickListener.onItemClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        return EquipmentViewHolder(
            EquipmentItemBinding.inflate(LayoutInflater.from(parent.context)),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        val equipment = items[position]
        holder.bind(equipment)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setEquipment(items: List<AmbulanceEquipment>) {
        this.items = items
    }
    fun getEquipment(position: Int): AmbulanceEquipment {
        return items[position]
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onPlusClick(position: Int)
        fun onMinusClick(position: Int)
    }
}
