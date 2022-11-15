package com.faircorp.datamodels
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.faircorp.FaircorpApplication
import com.faircorp.dao.BuildingDao
import com.faircorp.dto.BuildingDto
import com.faircorp.model.Building
import com.faircorp.serviceApi.ServicesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext




class BuildingDataModel(private val buildingDao: BuildingDao) : RootDataModel() {
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val buildingDao = (extras[APPLICATION_KEY] as FaircorpApplication).database.buildingDao()
                return BuildingDataModel(buildingDao) as T
            }
        }

    }


    //logical functionalities used for getting all building
    fun findAll(): LiveData<List<BuildingDto>> = liveData {
        val elements: List<BuildingDto> = withContext(Dispatchers.IO) {
            try {
                val response = ServicesApi.buildingServiceApi.findAll().execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val buildings: List<BuildingDto> = response.body() ?: emptyList()
                buildings.apply {
                    buildingDao.clearAll()
                    forEach {
                        buildingDao.create(
                            Building(
                                id = it.id,
                                name = it.name,
                                address = it.address
                            )
                        )
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                buildingDao.findAll().map { it.toDto() }
            }
        }
        emit(elements)
    }



    //logical functionalities used for create Building
    fun createBuilding( building:BuildingDto) = liveData {
        val element: BuildingDto = withContext(Dispatchers.IO) {
            try {
                val response = ServicesApi.buildingServiceApi.createBuilding(building).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val building: BuildingDto = response.body() ?: throw Exception("No response body")
                buildingDao.create(Building(id = building.id , name = building.name, address = building.address))
                building
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                building

            }
        }
        emit(element)
    }




    //logical functionalities used for deleting building
    fun deleteBuilding(buildingId: Long) = liveData {
        val element: BuildingDto = withContext(Dispatchers.IO) {
            try {
                val response = ServicesApi.buildingServiceApi.deleteBuilding(buildingId).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val building: BuildingDto = (response.body() ?: throw Exception("No response body")) as BuildingDto
                buildingDao.deleteById(buildingId)
                building
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                BuildingDto(id = buildingId, name = "", address = "")

            }
        }
        emit(element)
    }



}