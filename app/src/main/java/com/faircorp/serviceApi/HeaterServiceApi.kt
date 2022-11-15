package com.faircorp.serviceApi
import com.faircorp.dto.HeaterDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path




interface HeaterServiceApi {


    //For getting all heaters
    @GET("/api/heaters")
    fun findAll(): Call<List<HeaterDto>>

    //For creating heater
    @POST("/api/heaters")
    fun createHeater(@Body heater: HeaterDto): Call<HeaterDto>

    //For deleting heater
    @DELETE("/api/heaters/{heaterId}")
    fun deleteHeater(@Path("heaterId") id: Long): Call<Void>

    //For changing switch of heater
    @POST("/api/heaters/{heaterId}/switch")
    fun switchStatus(@Path("heaterId") heater_id: Long): Call<HeaterDto>

    //For getting heaters by Room Id
    @GET("/api/heaters/room/{roomId}")
    fun findHeatersByRoomId(@Path("roomId") room_id: Long): Call<List<HeaterDto>>
}