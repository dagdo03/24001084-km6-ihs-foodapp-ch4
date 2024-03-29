package com.example.foodapp.data.datasource.category

import com.example.foodapp.data.model.Category

class CategoryDataSourceImpl : CategoryDataSource {
    override fun getCategoryData(): List<Category> {
        return mutableListOf(
            Category(imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/category_img/ic_burger.png?raw=true", name = "Burger"),
            Category(imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/category_img/ic_cake.png?raw=true", name = "Cake"),
            Category(imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/category_img/ic_ramen.png?raw=true", name = "Ramen"),
            Category(imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/category_img/ic_salad.png?raw=true", name = "Salad"),
            Category(imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/category_img/ic_sushi.png?raw=true", name = "Sushi"),
            Category(imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/category_img/ic_steak.png?raw=true", name = "Steak"),
            Category(imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/category_img/ic_pizza.png?raw=true", name = "Pizza"),
            Category(imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/category_img/ic_pasta.png?raw=true", name = "Pasta")
        )

    }
}