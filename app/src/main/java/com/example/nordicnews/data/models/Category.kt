package com.example.nordicnews.data.models

import com.example.nordicnews.R

interface Category {
    val category: String
    val icon: Int
}

data class General(
    override val category: String = "General",
    override val icon: Int = R.drawable.ic_general,
    ) : Category

data class Business(
    override val category: String = "Business",
    override val icon: Int = R.drawable.ic_business,
) : Category

data class Technology(
    override val category: String = "Technology",
    override val icon: Int = R.drawable.ic_tech,
) : Category

data class Sports(
    override val category: String = "Sports",
    override val icon: Int = R.drawable.ic_tech,
) : Category


