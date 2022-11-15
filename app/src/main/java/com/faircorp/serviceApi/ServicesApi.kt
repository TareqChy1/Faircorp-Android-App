package com.faircorp.serviceApi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory




const val API_USERNAME = "user"
const val API_PASSWORD = "password"




object ServicesApi {


    //For Authenticating the server
    val credentials = Credentials.basic(API_USERNAME, API_PASSWORD)
    val interceptor = Interceptor { chain -> val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials).build()
        chain.proceed(authenticatedRequest)
    }



    val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(logging).build()



    val buildingServiceApi : BuildingServiceApi by lazy {

        Retrofit.Builder()
            .baseUrl("https://faircorp-tareq-chy.cleverapps.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(BuildingServiceApi::class.java)
    }



    val roomServiceApi : RoomServiceApi by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://faircorp-tareq-chy.cleverapps.io/")
            .client(client)
            .build()
            .create(RoomServiceApi::class.java)
    }



    val windowServiceApi : WindowServiceApi by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://faircorp-tareq-chy.cleverapps.io/")
            .client(client)
            .build()
            .create(WindowServiceApi::class.java)
    }



    val heaterServiceApi : HeaterServiceApi by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://faircorp-tareq-chy.cleverapps.io/")
            .client(client)
            .build()
            .create(HeaterServiceApi::class.java)
    }

}