package com.example.foodapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.data.model.Menu
import com.example.foodapp.databinding.LayoutItemMenuGridBinding
import com.example.foodapp.databinding.LayoutItemMenuListBinding
import com.example.foodapp.presentation.home.adapter.viewholder.MenuGridItemViewHolder
import com.example.foodapp.presentation.home.adapter.viewholder.MenuListItemViewHolder

interface OnItemClickedListener<T> {
    fun onItemClicked(item: T)
}

class MenuListAdapter(
    private var listMode: Int,
    private val itemClick: (Menu) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val MODE_LIST = 0
        const val MODE_GRID = 1
    }

    private var asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Menu>() {
                override fun areItemsTheSame(
                    oldItem: Menu,
                    newItem: Menu,
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: Menu,
                    newItem: Menu,
                ): Boolean {
                    return oldItem.hashCode() == oldItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Menu>) {
        asyncDataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return if (listMode == MODE_GRID) {
            MenuGridItemViewHolder(
                LayoutItemMenuGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                itemClick,
            )
        } else {
            MenuListItemViewHolder(
                LayoutItemMenuListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                itemClick,
            )
        }
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is MenuGridItemViewHolder -> holder.bind(asyncDataDiffer.currentList[position])
            is MenuListItemViewHolder -> holder.bind(asyncDataDiffer.currentList[position])
        }
    }

    fun updateListMode(newListMode: Int) {
        listMode = newListMode
    }
}
