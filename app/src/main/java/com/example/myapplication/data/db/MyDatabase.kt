package com.example.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.data.db.dao.SampleDao

@Database(
    entities = [SampleDao::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun sampleDao(): SampleDao
}