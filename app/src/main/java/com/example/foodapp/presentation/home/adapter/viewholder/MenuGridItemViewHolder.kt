package com.example.foodapp.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodapp.core.ViewHolderBinder
import com.example.foodapp.data.model.Menu
import com.example.foodapp.databinding.LayoutItemMenuGridBinding
import com.example.foodapp.presentation.home.adapter.OnItemClickedListener
import com.example.foodapp.utils.toIndonesianFormat

class MenuGridItemViewHolder(
    private val binding: LayoutItemMenuGridBinding,
    private val listener: OnItemClickedListener<Menu>
) : RecyclerView.ViewHolder(binding.root),
    ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        item.let {
            binding.ivMenuImage.load(it.imgURL) {
                crossfade(true)
            }
            binding.tvMenuName.text = it.name
            binding.tvMenuPrice.text = it.price.toIndonesianFormat()

            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}