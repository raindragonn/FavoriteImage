package com.raindragonn.favoriteimage.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raindragonn.favoriteimage.data.database.entity.ImageData
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageData: ImageData): Long

    @Query("DELETE FROM like_image WHERE image_id = :imageId")
    suspend fun deleteByImageId(imageId: String)

    @Query("SELECT * FROM like_image WHERE id = :id")
    suspend fun getById(id: Long): ImageData?

    @Query("SELECT * FROM like_image WHERE image_id = :imageId")
    suspend fun getByImageId(imageId: String): ImageData?

    @Query("SELECT * FROM like_image")
    fun getAllLikedImageFlow(): Flow<List<ImageData>>

    @Query("SELECT * FROM like_image")
    fun getAllLikedImage(): List<ImageData>
}