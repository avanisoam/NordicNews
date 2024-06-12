package com.avanisoam.nordicnews.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.avanisoam.nordicnews.data.model.Article

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(ArticleTypeConvertor::class)
abstract class NordicNewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

}