package com.raindragonn.favoriteimage.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raindragonn.favoriteimage.data.database.dao.ImageDao
import com.raindragonn.favoriteimage.data.database.entity.ImageData

@Database(
    entities = [ImageData::class],
    version = DatabaseConfig.VERSION,
    exportSchema = false,
)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun getImageDao(): ImageDao

    companion object {
        fun buildDataBase(
            context: Context
        ): ImageDatabase =
            Room.databaseBuilder(
                context,
                ImageDatabase::class.java,
                DatabaseConfig.DATABASE_NAME
            ).build()
    }
}