package com.example.foodapp.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodapp.core.ViewHolderBinder
import com.example.foodapp.data.model.Menu
import com.example.foodapp.databinding.LayoutItemMenuListBinding
import com.example.foodapp.utils.toIndonesianFormat

class MenuListItemViewHolder(
    private val binding: LayoutItemMenuListBinding,
    private val itemClick: (Menu) -> Unit,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        item.let {
            binding.ivMenuImage.load(it.imgURL) {
                crossfade(true)
            }
            binding.tvMenuName.text = it.name
            binding.tvMenuPrice.text = it.price.toIndonesianFormat()

            itemView.setOnClickListener {
                itemClick(item)
            }
        }
    }
}
