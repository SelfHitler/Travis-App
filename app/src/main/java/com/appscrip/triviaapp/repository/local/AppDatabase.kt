package com.appscrip.triviaapp.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appscrip.triviaapp.repository.local.dao.QuizDao
import com.appscrip.triviaapp.repository.local.entities.QuizDetailEntity

@Database(entities = [QuizDetailEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, QuizDataConverter::class)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun quizDao(): QuizDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "Travia_App_Database"
                        ).fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return instance
        }

    }
}