package com.faircorp.datamodels
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.faircorp.FaircorpApplication
import com.faircorp.dao.WindowDao
import com.faircorp.dto.WindowDto
import com.faircorp.dto.WindowStatus
import com.faircorp.model.Window
import com.faircorp.serviceApi.ServicesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext





class WindowDataModel(private val windowDao: WindowDao) : RootDataModel() {

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val windowDao = (extras[APPLICATION_KEY] as FaircorpApplication).database.windowDao()
                return WindowDataModel(windowDao) as T
            }
        }
    }



    //logical functionalities used for getting window by RoomId
    fun findByRoomId(roomId: Long): LiveData<List<WindowDto>> = liveData {
        val elements: List<WindowDto> = withContext(Dispatchers.IO) {
            try {
                val response = ServicesApi.windowServiceApi.findWindowsByRoomId(roomId).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val windows: List<WindowDto> = response.body() ?: emptyList()
                windows.apply {
                    Log.e("windows", windows.toString())
                    windowDao.clearByRoomId(roomId)
                    forEach {
                        windowDao.create(
                            Window(
                                id = it.id,
                                name = it.name,
                                roomId = it.roomId,
                                roomName = it.roomName,
                                windowStatus = it.windowStatus
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                windowDao.findByRoomId(roomId).map { it.toDto() }
            }
        }
        emit(elements)
    }




    //logical functionalities used for create window
    fun createWindow(window: WindowDto) = liveData {
        val element: WindowDto = withContext(Dispatchers.IO) {
            try {
                val response = ServicesApi.windowServiceApi.createWindow(window).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val window: WindowDto = response.body() ?: window
                window.apply {
                    windowDao.create(
                        Window(
                            id = id,
                            name = name,
                            roomId = roomId,
                            roomName = roomName,
                            windowStatus = windowStatus
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                windowDao.findById(window.id!!)!!.toDto()
            }
        }
        emit(element)
    }




    //logical functionalities used for deleting window
    fun deleteWindow(windowId: Long) = liveData {
        val element: WindowDto = withContext(Dispatchers.IO) {
            try {
                val response = ServicesApi.windowServiceApi.deleteById(windowId).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val window: WindowDto = (response.body() ?: throw Exception("Not found")) as WindowDto
                window.apply {
                    windowDao.deleteById(windowId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                WindowDto(id = windowId, name = "Unknown", roomId = 0, roomName = "Unknown", windowStatus = WindowStatus.CLOSED)
            }
        }
        emit(element)
    }


}