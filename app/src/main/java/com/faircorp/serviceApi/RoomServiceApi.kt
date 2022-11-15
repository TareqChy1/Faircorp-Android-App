package com.faircorp.serviceApi
import com.faircorp.dto.RoomDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path




interface RoomServiceApi {


    //For getting all rooms
    @GET("/api/rooms")
    fun findAll(): Call<List<RoomDto>>

    //For creating room
    @POST("/api/rooms")
    fun createRoom(@Body room: RoomDto): Call<RoomDto>

    //For deleting room
    @DELETE("/api/rooms/{roomId}")
    fun deleteRoom(@Path("roomId") id: Long): Call<Void>

    //For getting all rooms by  building id
    @GET("/api/rooms/building/{buildingId}")
    fun findAllRoomsByBuilding(@Path("buildingId") buildingId: Long): Call<List<RoomDto>>
}