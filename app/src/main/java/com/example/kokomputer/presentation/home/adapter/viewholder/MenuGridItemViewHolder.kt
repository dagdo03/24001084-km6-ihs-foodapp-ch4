package com.example.kokomputer.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kokomputer.core.ViewHolderBinder
import com.example.kokomputer.data.model.Menu
import com.example.kokomputer.databinding.LayoutItemMenuGridBinding
import com.example.kokomputer.presentation.home.adapter.OnItemClickedListener
import com.example.kokomputer.utils.toIndonesianFormat

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