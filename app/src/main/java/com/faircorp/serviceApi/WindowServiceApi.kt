package com.faircorp.serviceApi
import com.faircorp.dto.WindowDto
import retrofit2.Call
import retrofit2.http.*




interface WindowServiceApi {

    //For getting all window
    @GET("/api/windows")
    fun findAll(): Call<List<WindowDto>>

    //For creating window
    @POST("/api/windows")
    fun createWindow(@Body window: WindowDto): Call<WindowDto>

    //For deleting window
    @DELETE("/api/windows/{windowId}")
    fun deleteById(@Path("windowId") window_id: Long): Call<Void>

    //For changing status of window
    @POST("/api/windows/{windowId}/switch")
    fun switchStatus(@Path("windowId") window_id: Long): Call<WindowDto>

    //For getting window by RoomId
    @GET("/api/windows/room/{roomId}")
    fun findWindowsByRoomId(@Path("roomId") room_id: Long): Call<List<WindowDto>>

}