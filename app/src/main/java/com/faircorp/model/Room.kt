package com.faircorp.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faircorp.dto.RoomDto

//Room table
@Entity(tableName = "room_table")
data class Room(

    //Room attributes
    @PrimaryKey val id: Long?,
    @ColumnInfo(name = "roomName") val name: String?,
    @ColumnInfo(name = "roomFloor") val floor: Long?,
    @ColumnInfo(name = "roomCurrentTemperature") val currentTemperature: Double?,
    @ColumnInfo(name = "roomTargetTemperature") val targetTemperature: Double?,
    @ColumnInfo(name = "buildingId") val buildingId: Long?)

{
    fun toDto(): RoomDto =
        RoomDto(id, name, floor, currentTemperature, targetTemperature, buildingId)
}