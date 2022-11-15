package com.faircorp.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faircorp.dto.HeaterDto
import com.faircorp.dto.HeaterStatus



//heater table
@Entity(tableName = "heater")
data class Heater(


    //Heater attributes
    @PrimaryKey val id: Long?,
    @ColumnInfo val name: String,
    @ColumnInfo(name = "roomId") val roomId: Long,
    @ColumnInfo(name = "power") val power: Long?,
    @ColumnInfo(name = "heaterStatus") val heaterStatus: HeaterStatus)


{
    fun toDto(): HeaterDto = HeaterDto(
            id,
            name,
            power,
            roomId,
            heaterStatus
        )

}