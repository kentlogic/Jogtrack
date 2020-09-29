package com.kentlogic.jogtrack.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kentlogic.jogtrack.db.Jog
import com.kentlogic.jogtrack.repositories.MainRepository
import com.kentlogic.jogtrack.util.SortType
import kotlinx.coroutines.launch



class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
): ViewModel() {

    private val jogsSortedByDate = mainRepository.getAllRunsSortedByDate()
    private val jogsSortedByDistance = mainRepository.getAllRunsSortedByDistance()
    private val jogsSortedByCaloriesBurned = mainRepository.getAllRunsSortedByCaloriesBurned()
    private val jogsSortedByTimeInMillis = mainRepository.getAllRunsSortedByTimeInMillis()
    private val jogsSortedByAvgSpeed = mainRepository.getAllRunsSortedByAvgSpeed()

    val jogs = MediatorLiveData<List<Jog>>()
    var sortType = SortType.DATE

    init {
        //Use the source based on the type and assign the result

        jogs.addSource(jogsSortedByDate) { result ->
            if (sortType == SortType.DATE) {
                result?.let {
                    jogs.value = it
                }
            }
        }

        jogs.addSource(jogsSortedByDistance) { result ->
            if (sortType == SortType.DISTANCE) {
                result?.let {
                    jogs.value = it
                }
            }
        }

        jogs.addSource(jogsSortedByCaloriesBurned) { result ->
            if (sortType == SortType.CALORIES_BURNED) {
                result?.let {
                    jogs.value = it
                }
            }
        }

        jogs.addSource(jogsSortedByTimeInMillis) { result ->
            if (sortType == SortType.RUNNING_TIME) {
                result?.let {
                    jogs.value = it
                }
            }
        }

        jogs.addSource(jogsSortedByAvgSpeed) { result ->
            if (sortType == SortType.AVG_SPEED) {
                result?.let {
                    jogs.value = it
                }
            }
        }
    }


    //Return the sorted jogs based on the sort type and update the sort type
    fun sortJogs(sortType: SortType) = when(sortType) {
        SortType.DATE -> jogsSortedByDate.value?.let { jogs.value = it }
        SortType.RUNNING_TIME -> jogsSortedByTimeInMillis.value?.let { jogs.value = it }
        SortType.CALORIES_BURNED -> jogsSortedByCaloriesBurned.value?.let { jogs.value = it }
        SortType.DISTANCE -> jogsSortedByDistance.value?.let { jogs.value = it }
        SortType.AVG_SPEED -> jogsSortedByAvgSpeed.value?.let { jogs.value = it }
    }.also {
        this.sortType = sortType
    }

    fun insertJog(jog: Jog) = viewModelScope.launch {
           mainRepository.insertJog(jog)
    }
}