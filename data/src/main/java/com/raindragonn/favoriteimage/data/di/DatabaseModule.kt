package com.raindragonn.favoriteimage.data.di

import android.content.Context
import com.raindragonn.favoriteimage.data.database.ImageDatabase
import com.raindragonn.favoriteimage.data.database.dao.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ): ImageDatabase {
        return ImageDatabase.buildDataBase(context)
    }

    @Provides
    @Singleton
    fun providesImageDao(
        database: ImageDatabase
    ): ImageDao {
        return database.getImageDao()
    }
}