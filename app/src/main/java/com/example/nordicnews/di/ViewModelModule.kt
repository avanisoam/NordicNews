package com.example.nordicnews.di

import com.example.nordicnews.ui.bookmark.BookmarksViewModel
import com.example.nordicnews.ui.detail.DetailViewModel
import com.example.nordicnews.ui.developerOptions.DeveloperOptionsViewModel
import com.example.nordicnews.ui.home.HomeViewModel
import com.example.nordicnews.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        BookmarksViewModel(get())
    }

    viewModel{
        DetailViewModel(get(), get())
    }

    viewModel {
        HomeViewModel(get(), get())
    }

    viewModel {
        DeveloperOptionsViewModel(get())
    }

    viewModel{
        SearchViewModel(get(), get())
    }
}