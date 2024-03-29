package com.example.kokomputer.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kokomputer.core.ViewHolderBinder
import com.example.kokomputer.data.model.Menu
import com.example.kokomputer.databinding.LayoutItemMenuGridBinding
import com.example.kokomputer.databinding.LayoutItemMenuListBinding
import com.example.kokomputer.presentation.home.adapter.viewholder.MenuGridItemViewHolder
import com.example.kokomputer.presentation.home.adapter.viewholder.MenuListItemViewHolder
import com.example.kokomputer.utils.toIndonesianFormat

interface OnItemClickedListener<T> {
    fun onItemClicked(item: T)
}

class MenuListAdapter(
    private val listMode: Int = MODE_LIST,
    private val listener: OnItemClickedListener<Menu>,
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val MODE_LIST = 0
        const val MODE_GRID = 1
    }

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Menu>() {
                override fun areItemsTheSame(
                    oldItem: Menu,
                    newItem: Menu
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Menu,
                    newItem: Menu
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
        )

    fun submitData(data: List<Menu>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (listMode == MODE_GRID) {
            MenuGridItemViewHolder(
                LayoutItemMenuGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listener
            )
        } else {
            MenuListItemViewHolder(
                LayoutItemMenuListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listener
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolderBinder<*>) return
        (holder as ViewHolderBinder<Menu>).bind(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemMenuViewHolder(
        private val binding: LayoutItemMenuGridBinding,
        val itemClick: (Menu) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Menu) {
            with(item) {
                binding.ivMenuImage.load(item.imgURL) {
                    crossfade(true)
                }
                binding.tvMenuName.text = item.name
                binding.tvMenuPrice.text = item.price.toIndonesianFormat()
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}