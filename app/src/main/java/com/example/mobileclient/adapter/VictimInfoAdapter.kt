package com.example.mobileclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.databinding.ListItemVictimInfoBinding
import com.example.mobileclient.model.Casualty

class VictimInfoAdapter(
    private var items: List<Casualty>,
    private var onItemClickListener: OnItemClickListener,
) :
    RecyclerView.Adapter<VictimInfoAdapter.VictimInfoViewHolder>() {

    class VictimInfoViewHolder(
        private var binding: ListItemVictimInfoBinding,
        private var itemClickListener: OnItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Casualty) {
            binding.victim = item
            binding.root.setOnClickListener(this)
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            itemClickListener.onItemClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VictimInfoViewHolder {
        return VictimInfoViewHolder(
            ListItemVictimInfoBinding.inflate(LayoutInflater.from(parent.context)),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: VictimInfoViewHolder, position: Int) {
        val equipment = items[position]
        holder.bind(equipment)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setCasualties(items: List<Casualty>) {
        this.items = items
    }
    fun getCasualties(position: Int): Casualty {
        return items[position]
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
