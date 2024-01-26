package com.raindragonn.favoriteimage.data.di

import com.raindragonn.favoriteimage.data.database.dao.ImageDao
import com.raindragonn.favoriteimage.data.remote.api.SearchApi
import com.raindragonn.favoriteimage.data.repository.ImageRepositoryImpl
import com.raindragonn.favoriteimage.domain.di.IoDispatcher
import com.raindragonn.favoriteimage.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesImageRepository(
        @IoDispatcher
        dispatcher: CoroutineDispatcher,
        api: SearchApi,
        dao: ImageDao,
    ): ImageRepository {
        return ImageRepositoryImpl(
            dispatcher,
            api,
            dao
        )
    }
}