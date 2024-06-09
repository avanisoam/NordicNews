package com.example.nordicnews.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nordicnews.data.model.Article

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(ArticleTypeConvertor::class)
abstract class NordicNewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

}