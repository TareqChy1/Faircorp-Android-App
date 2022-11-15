package com.faircorp.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.faircorp.model.Building

@Dao
interface BuildingDao {

    //For displaying building list based on building Name
    @Query("select * from building order by buildingName")
    fun findAll(): List<Building>

    //For getting building based on buildingId
    @Query("select * from building where id = :id")
    fun findById(id: Long): Building?

    //For creating building
    @Insert
    suspend fun create(building: Building)

    //For deleting building
    @Query("DELETE FROM building")
    suspend fun clearAll()

    //For deleting  building by building id
    @Query("DELETE FROM building WHERE id = :id")
    suspend fun deleteById(id: Long)


}