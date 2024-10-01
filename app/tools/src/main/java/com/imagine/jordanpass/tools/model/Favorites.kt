package com.imagine.jordanpass.tools.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorites(
    @PrimaryKey @ColumnInfo(name = "place_id") val id: Int,
//    @ColumnInfo(name = "place_id") val firstName: Int?,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean?
)
