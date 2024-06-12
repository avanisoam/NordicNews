package com.avanisoam.nordicnews.di

import com.avanisoam.nordicnews.ui.bookmark.BookmarksViewModel
import com.avanisoam.nordicnews.ui.detail.DetailViewModel
import com.avanisoam.nordicnews.ui.developerOptions.DeveloperOptionsViewModel
import com.avanisoam.nordicnews.ui.home.HomeViewModel
import com.avanisoam.nordicnews.ui.search.SearchViewModel
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