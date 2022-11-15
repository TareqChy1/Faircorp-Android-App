package com.faircorp.datamodels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


abstract class RootDataModel :ViewModel() {

    val networkState: MutableLiveData<State> by lazy {

        MutableLiveData<State>().also { it.value = State.ONLINE }
    }

    enum class State { ONLINE, OFFLINE }
}