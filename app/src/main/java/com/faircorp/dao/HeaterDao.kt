package com.faircorp.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.faircorp.model.Heater


@Dao
interface HeaterDao {

    //For displaying heater list based on heater Name
    @Query("select * from heater order by name")
    fun findAll(): List<Heater>

    //For getting heater based on roomId
    @Query("select * from heater where roomId = :roomId order by name")
    fun findHeatersByRoomId(roomId: Long): List<Heater>

    //For getting heater heaterId
    @Query("select * from heater where id = :id")
    fun findById(id: Long): Heater?

    //For creating heater
    @Insert
    suspend fun create(heater: Heater)

    //For deleting heaters by roomId
    @Query("delete from heater where roomId = :roomId")
    suspend fun clearByRoomId(roomId: Long)

    //For getting heater status
    @Query("select * from heater where heaterStatus = :status")
    fun findAllHeatersByStatus(status: String): List<Heater>

    //For deleting heater
    @Query("DELETE FROM heater WHERE id = :id")
    suspend fun deleteById(id: Long)

}