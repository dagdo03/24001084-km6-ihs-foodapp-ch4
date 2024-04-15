package com.example.foodapp.data.mapper

import com.example.foodapp.data.model.Category
import com.example.foodapp.data.source.network.model.category.CategoryItemResponse
import java.util.UUID

fun CategoryItemResponse?.toCategory() = Category(
    imgURL = this?.imageUrl.orEmpty(),
    name = this?.nama.orEmpty()
)

fun Collection<CategoryItemResponse>?.toCategories() = this?.map { it.toCategory() } ?: listOf()