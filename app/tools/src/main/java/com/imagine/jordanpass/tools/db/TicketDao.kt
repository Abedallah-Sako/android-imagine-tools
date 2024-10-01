package com.imagine.jordanpass.tools.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imagine.jordanpass.tools.model.Eticket
import com.imagine.jordanpass.tools.model.Favorites
import com.imagine.jordanpass.tools.model.Ticket

@Dao
interface TicketDao {
    @Query("SELECT * FROM Ticket")
     fun getJordanPassTickets(): LiveData<List<Ticket>>

    @Query("SELECT * FROM Eticket")
     fun getJordanTicketTickets(): LiveData<List<Eticket>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertJordanPassTicket(ticket: Ticket)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJordanPassTicketList(ticketList: List<Ticket>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJordanTicketTicket(eTicket: Eticket)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJordanTicketTicketList(eTicketList: List<Eticket>)

    @Delete
    suspend fun deleteJordanPassTicket(ticket: Ticket)

    @Delete
    suspend fun deleteJordanTicketTicket(eTicket: Eticket)

}