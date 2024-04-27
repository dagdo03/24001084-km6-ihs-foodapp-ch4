package com.example.foodapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodapp.data.model.Category
import com.example.foodapp.databinding.LayoutItemCategoryBinding

class CategoryListAdapter(private val itemClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryListAdapter.ItemCategoryViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Category>() {
                override fun areItemsTheSame(
                    oldItem: Category,
                    newItem: Category,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Category,
                    newItem: Category,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Category>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemCategoryViewHolder {
        val binding =
            LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCategoryViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemCategoryViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemCategoryViewHolder(
        private val binding: LayoutItemCategoryBinding,
        val itemClick: (Category) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Category) {
            with(item) {
                binding.ivCategoryImage.load(item.imgURL) {
                    crossfade(true)
                }
                binding.tvCategoryName.text = item.name
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
