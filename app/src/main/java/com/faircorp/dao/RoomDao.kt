package com.faircorp.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.faircorp.model.Room



@Dao
interface RoomDao {

    //For getting all room list
    @Query("select * from room_table order by roomName")
    fun findAll(): List<Room>

    //For getting rooms by buildingId
    @Query("select * from room_table where buildingId = :buildingId order by roomName")
    fun findAllRoomsByBuilding(buildingId: Long): List<Room>

    //For creating room
    @Insert
    suspend fun create(room: Room)

    //For getting room by roomId
    @Query("select * from room_table where id = :id")
    fun findById(id: Long): Room?

    //For deleting rooms by buildingId
    @Query("delete from room_table where buildingId = :buildingId")
    suspend fun clearByBuildingId(buildingId: Long)

    //For deleting room
    @Query("DELETE FROM room_table WHERE id = :id")
    suspend fun deleteById(id: Long)


}