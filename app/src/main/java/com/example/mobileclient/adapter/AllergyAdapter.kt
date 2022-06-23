package com.example.mobileclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.R
import com.example.mobileclient.model.Allergy

class AllergyAdapter(private val dataset: List<Allergy>) :
    RecyclerView.Adapter<AllergyAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.alergy_text)
        val textView1: TextView = view.findViewById(R.id.alergy_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_allergies, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.allergyName
        holder.textView1.text = item.allergyType
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}