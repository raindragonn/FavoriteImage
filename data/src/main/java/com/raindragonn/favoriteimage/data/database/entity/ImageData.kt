package com.raindragonn.favoriteimage.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "like_image")
data class ImageData(
    @PrimaryKey val id: Long? = null,
    @ColumnInfo("image_id") val imageId: String,
    @ColumnInfo("thumbnail_url") val thumbnailUrl: String,
    @ColumnInfo("origin_url") val originUrl: String,
    @ColumnInfo("author") val author: String,
    @ColumnInfo("width") val width: Int,
    @ColumnInfo("height") val height: Int,
    @ColumnInfo("created_at") val createdAt: String,
    @ColumnInfo("liked") val liked: Boolean = false,
)
