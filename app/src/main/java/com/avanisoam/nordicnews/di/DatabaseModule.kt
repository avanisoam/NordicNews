package com.avanisoam.nordicnews.di

import android.app.Application
import androidx.room.Room
import com.avanisoam.nordicnews.data.database.ArticleDao
import com.avanisoam.nordicnews.data.database.ArticleTypeConvertor
import com.avanisoam.nordicnews.data.database.NordicNewsDatabase
import org.koin.dsl.module

fun provideDataBase(application: Application): NordicNewsDatabase =
    Room.databaseBuilder(
        application,
        NordicNewsDatabase::class.java,
        "nordicnews_database"
    )
        .fallbackToDestructiveMigration()
        .addTypeConverter(ArticleTypeConvertor())
        .build()

fun provideDao(nordicNewsDataBase: NordicNewsDatabase): ArticleDao =
    nordicNewsDataBase.articleDao()


val dataBaseModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}