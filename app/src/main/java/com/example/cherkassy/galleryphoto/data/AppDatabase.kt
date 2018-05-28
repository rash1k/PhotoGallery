package com.example.cherkassy.galleryphoto.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = [PhotoItem::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    companion object {
        const val DATABASE_NAME = "images-db"
    }
}