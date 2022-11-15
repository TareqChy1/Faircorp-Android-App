package com.faircorp.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faircorp.dto.BuildingDto


//Building table name
@Entity(tableName = "building")
data class Building(

    //Building attributes
    @PrimaryKey val id: Long?,
    @ColumnInfo(name = "buildingName") val name: String,
    @ColumnInfo(name = "buildingAddress") val address: String)

{
    fun toDto(): BuildingDto =BuildingDto(id, name, address)
}