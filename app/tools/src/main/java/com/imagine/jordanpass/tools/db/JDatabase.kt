package com.imagine.jordanpass.tools.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.imagine.jordanpass.tools.model.Eticket
import com.imagine.jordanpass.tools.model.Favorites
import com.imagine.jordanpass.tools.model.Ticket

@Database(entities = [Eticket::class,Ticket::class, Favorites::class], version = 1)
abstract class JDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun ticketDao(): TicketDao
}