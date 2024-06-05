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

    companion object {
        @Volatile
        private var Instance: NordicNewsDatabase? = null

        fun getDatabase(context: Context): NordicNewsDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    NordicNewsDatabase::class.java,
                    "nordicnews_database"
                )
                    .addTypeConverter(ArticleTypeConvertor())
                    .build()
                    .also { Instance = it }
            }
        }
    }
}