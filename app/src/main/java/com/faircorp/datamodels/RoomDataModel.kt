package com.faircorp.datamodels
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.faircorp.FaircorpApplication
import com.faircorp.dao.RoomDao
import com.faircorp.dto.RoomDto
import com.faircorp.model.Room
import com.faircorp.serviceApi.ServicesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext




class RoomDataModel(private val roomDao: RoomDao) : RootDataModel() {

    companion object {

        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val roomDao = (extras[APPLICATION_KEY] as FaircorpApplication).database.roomDao()

                return RoomDataModel(roomDao) as T
            }

        }

    }



    //logical functionalities used for getting all room
    fun findAll(): LiveData<List<RoomDto>> = liveData {
        val elements: List<RoomDto> = withContext(Dispatchers.IO) {
            try {
                val response = ServicesApi.roomServiceApi.findAll().execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val rooms: List<RoomDto> = response.body() ?: emptyList()
                rooms.apply {
                    forEach {
                        roomDao.create(
                            Room(
                                id = it.id,
                                name = it.name,
                                floor = it.floor,
                                currentTemperature = it.currentTemperature,
                                targetTemperature = it.targetTemperature,
                                buildingId = it.buildingId


                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                roomDao.findAll().map { it.toDto() }
            }
        }
        emit(elements)
    }



    //logical functionalities used for getting room by BuildingId
    fun findByBuildingId(buildingId: Long): LiveData<List<RoomDto>> = liveData {
        val elements: List<RoomDto> = withContext(Dispatchers.IO) {
            try {
                val response = ServicesApi.roomServiceApi.findAllRoomsByBuilding(buildingId).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val rooms: List<RoomDto> = response.body() ?: emptyList()
                if(rooms.isEmpty()) {
                    roomDao.clearByBuildingId(buildingId)
                }
                rooms.apply {
                    roomDao.clearByBuildingId(buildingId)
                    forEach {
                        roomDao.create(
                            Room(
                                id = it.id,
                                name = it.name,
                                floor = it.floor,
                                currentTemperature = it.currentTemperature,
                                targetTemperature = it.targetTemperature,
                                buildingId = it.buildingId
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                roomDao.findAllRoomsByBuilding(buildingId).map { it.toDto() }
            }
        }
        emit(elements)
    }


    //logical functionalities used for createRoom
    fun createRoom(room: RoomDto) = liveData {
        val element: RoomDto = withContext(Dispatchers.IO) {
            try {
                val response = ServicesApi.roomServiceApi.createRoom(room).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val room: RoomDto = response.body() ?: room
                room.apply {
                    roomDao.create(
                        Room(
                            id = id,
                            name = name,
                            floor = floor,
                            currentTemperature = currentTemperature,
                            targetTemperature = targetTemperature,
                            buildingId = buildingId
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                room
            }
        }
        emit(element)
    }


    //logical functionalities used for deleteRoom
    fun deleteRoom(roomId: Long) = liveData {
        val element: RoomDto = withContext(Dispatchers.IO) {
            try {
                val response = ServicesApi.roomServiceApi.deleteRoom(roomId).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val room: RoomDto = (response.body() ?: throw Exception("Not found")) as RoomDto
                room.apply {
                    roomDao.deleteById(roomId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                RoomDto(id = roomId, name = "", floor = 0, currentTemperature = 0.0, targetTemperature = 0.0, buildingId = 0)
            }
        }
        emit(element)
    }






}