package com.rpsouza.blogapp.presenter.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rpsouza.blogapp.databinding.ItemMapOptionBinding
import com.rpsouza.blogapp.domain.model.MapAppOption

class MapOptionAdapter(
    private val items: MutableList<MapAppOption>,
    private val onClick: (MapAppOption) -> Unit
) : RecyclerView.Adapter<MapOptionAdapter.VH>() {

    inner class VH(val vb: ItemMapOptionBinding) : RecyclerView.ViewHolder(vb.root) {
        fun bind(item: MapAppOption) = with(vb) {
            icon.setImageDrawable(item.icon)
            label.text = item.label
            root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val vb = ItemMapOptionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(vb)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newList: List<MapAppOption>) {
        // simples; se quiser performance, troque por DiffUtil
        items.clear()
        items.addAll(newList)
    }
}
