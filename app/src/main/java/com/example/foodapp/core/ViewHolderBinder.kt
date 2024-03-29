package com.example.foodapp.core

interface ViewHolderBinder<T> {
    fun bind(item: T)
}