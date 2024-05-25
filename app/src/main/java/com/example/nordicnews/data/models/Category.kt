package com.example.nordicnews.data.models

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
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

fun getTitleResource(activeCategory: Category): Int = when (activeCategory) {
    is General -> R.string.title_general
    is Business -> R.string.title_business
    is Technology -> R.string.title_technology
    else -> throw IllegalAccessException("Page number is invalid")
}

