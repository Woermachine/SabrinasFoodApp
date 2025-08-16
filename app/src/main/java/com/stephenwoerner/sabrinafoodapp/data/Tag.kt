package com.stephenwoerner.sabrinafoodapp.data

enum class Type {
    Ethnicity,
    FoodItem,
    Other
}

enum class Tag(val title: String, val type: Type) {
    Italian("Italian", Type.Ethnicity),
    Japanese("Japanese", Type.Ethnicity),
    American("American", Type.Ethnicity),
    Mexican("Mexican", Type.Ethnicity),
    Asian("Asian", Type.Ethnicity),
    Chinese("Chinese", Type.Ethnicity),
    Seafood("Seafood", Type.FoodItem),
    Sushi("Sushi", Type.FoodItem),
    Pizza("Pizza", Type.FoodItem),
    Salad("Salad", Type.FoodItem),
    Steak("Steak", Type.FoodItem),
    Burger("Burger", Type.FoodItem),
    Coffee("Coffee", Type.FoodItem),
    Sandwich("Sandwich", Type.FoodItem),
    Wings("Wings", Type.FoodItem),
    Hibachi("Hibachi", Type.FoodItem),
    Buffet("Buffet", Type.FoodItem),
    Milkshakes("Milkshakes", Type.FoodItem),
    IceCream("Ice Cream", Type.FoodItem),
    Wine("Wine", Type.FoodItem),
    Beer("Beer", Type.FoodItem),
    Dessert("Dessert", Type.FoodItem),
    Breakfast("Breakfast", Type.Other),
    Brunch("Brunch", Type.Other),
    Bar("Bar", Type.Other),
    FastFood("Fast Food", Type.Other),
    Delivery("Delivery", Type.Other),
}