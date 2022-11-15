package com.faircorp.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faircorp.dto.WindowDto
import com.faircorp.dto.WindowStatus


//Window table
@Entity(tableName = "rwindow")
data class Window(

    //Window attributes
    @PrimaryKey val id: Long?,
    @ColumnInfo val name: String,
    @ColumnInfo(name = "roomId") val roomId: Long,
    @ColumnInfo(name = "roomName") val roomName: String,
    @ColumnInfo(name = "windowStatus") val windowStatus: WindowStatus)

{
    fun toDto(): WindowDto =
        WindowDto(id, name,
            roomId,
            roomName,
            windowStatus)
}