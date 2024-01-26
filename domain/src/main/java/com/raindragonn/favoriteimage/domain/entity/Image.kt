package com.raindragonn.favoriteimage.domain.entity

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class Image(
    val id: String,
    val thumbnailUrl: String,
    val originUrl: String,
    val author: String,
    val width: Int,
    val height: Int,
    val createdAt: String,
    val liked: Boolean = false,
) : java.io.Serializable {
    val size: String
        get() = "$width x $height"

    val formattedTime: String
        get() {
            val formatter = DateTimeFormatter.ofPattern(TIME_FORMAT)
            val dateTime = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME)
            return dateTime.format(formatter)
        }

    companion object {
        private const val TIME_FORMAT = "yyyy/MM/dd HH:mm:ss"
    }
}
