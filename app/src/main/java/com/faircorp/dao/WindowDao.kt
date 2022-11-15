package com.faircorp.dao
import androidx.room.*
import com.faircorp.model.Window



@Dao
interface WindowDao {

    //For getting window List
    @Query("select * from rwindow order by name")
    fun findAll(): List<Window>

    //For getting window by window Id
    @Query("select * from rwindow where id = :id")
    fun findById(id: Long): Window?

    //For getting window by room id
    @Query("select * from rwindow where roomId = :roomId order by name")
    fun findByRoomId(roomId: Long): List<Window>

    //For creating window
    @Insert
    suspend fun create(window: Window)

    //For deleting all windows by using room Id
    @Query("delete from rwindow where roomId = :roomId")
    suspend fun clearByRoomId(roomId: Long)

    //For deleting window by id
    @Query("DELETE FROM rwindow WHERE id = :id")
    suspend fun deleteById(id: Long)
}