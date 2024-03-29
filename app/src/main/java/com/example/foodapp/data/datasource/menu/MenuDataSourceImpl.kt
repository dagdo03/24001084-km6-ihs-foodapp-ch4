package com.example.foodapp.data.datasource.menu

import com.example.foodapp.data.model.Menu

class MenuDataSourceImpl : MenuDataSource {
    override fun getMenuData(): List<Menu> {
        return mutableListOf(
            Menu(
                imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/menu_img/img_burger.jpg?raw=true",
                name = "Burger",
                price = 70000.00,
                description = "This burger is a normal burger",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationURL = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/menu_img/img_wagyu.jpg?raw=true",
                name = "Wagyu Steak",
                price = 80000.00,
                description = "This steak is made from premium wagyu",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationURL = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/menu_img/img_fruit_salad.jpg?raw=true",
                name = "Fruit Salad",
                price = 70000.00,
                description = "This fruit salad is made from fresh fruits",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationURL = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/menu_img/img_salad.jpg?raw=true",
                name = "Salad",
                price = 50000.00,
                description = "This salad is made from fresh vegetables",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationURL = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/menu_img/img_sushi_roll.jpg?raw=true",
                name = "Sushi Roll",
                price = 100000.00,
                description = "This sushi is made from fresh ingredients",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationURL = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/menu_img/img_burger_double_beef.jpg?raw=true",
                name = "Burger Double Beef",
                price = 90000.00,
                description = "This burger has double beef slice",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationURL = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/menu_img/img_burger_double_cheese.jpg?raw=true",
                name = "Burger Double Cheese",
                price = 75000.00,
                description = "This burger has double cheese slice",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationURL = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/menu_img/img_strawberry_cake.jpg?raw=true",
                name = "Strawberry Cake",
                price = 60000.00,
                description = "This cake is made from fresh strawberry",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationURL = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgURL = "https://github.com/dagdo03/KoKomputer-assets/blob/main/menu_img/img_chocolate_cake.jpg?raw=true",
                name = "Chocolate Cake",
                price = 70000.00,
                description = "This cake is made from premium chocolate",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationURL = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
        )
    }
}