package com.faircorp

import android.app.Application
import androidx.room.Room
import com.faircorp.dao.FaircorpDatabase


class FaircorpApplication : Application() {
    var count = 0
    val database: FaircorpDatabase by lazy {
        if (count == 0) {
            applicationContext.deleteDatabase("faircorpdb")
            count++
        }
        Room.databaseBuilder(this, FaircorpDatabase::class.java, "faircorpdb")
            .fallbackToDestructiveMigration()
            .build()
    }
}