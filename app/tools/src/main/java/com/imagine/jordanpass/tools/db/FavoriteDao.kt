package com.imagine.jordanpass.tools.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imagine.jordanpass.tools.model.Favorites

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites WHERE place_id LIKE :isFav")
     fun getFavorites(isFav:Int): Favorites

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<Favorites>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(favorites: Favorites)

    @Delete
     fun delete(favorites: Favorites)
}