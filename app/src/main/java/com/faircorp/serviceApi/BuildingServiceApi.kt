package com.faircorp.serviceApi
import com.faircorp.dto.BuildingDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path





interface BuildingServiceApi {


    //For getting all building
    @GET("/api/buildings")
    fun findAll(): Call<List<BuildingDto>>

    ////For creating  building
    @POST("/api/buildings")
    fun createBuilding(@Body building: BuildingDto): Call<BuildingDto>

    //For getting building by building id
    @GET("/api/buildings/{buildingId}")
    fun findById(@Path("buildingId") id: Long): Call<BuildingDto>

    //For deleting building
    @DELETE("/api/buildings/{buildingId}")
    fun deleteBuilding(@Path("buildingId") id: Long): Call<Void>

}