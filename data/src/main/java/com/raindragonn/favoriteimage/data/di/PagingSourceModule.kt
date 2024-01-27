package com.raindragonn.favoriteimage.data.di

import com.raindragonn.favoriteimage.data.pager.ImagePagingSource
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.pager.PagingSource
import com.raindragonn.favoriteimage.domain.pager.image.ImagePagingKey
import com.raindragonn.favoriteimage.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagingSourceModule {

    @Provides
    @Singleton
    fun providesImagePagingSource(
        repository: ImageRepository
    ): PagingSource<ImagePagingKey, Image> {
        return ImagePagingSource(
            repository
        )
    }
}