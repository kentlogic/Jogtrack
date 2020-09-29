package com.kentlogic.jogtrack.repositories

import com.kentlogic.jogtrack.db.Jog
import com.kentlogic.jogtrack.db.JogDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    val jogDao: JogDao
) {
    suspend fun insertJog(jog: Jog) = jogDao.insertJog(jog)

    suspend fun deleteJog(jog: Jog) = jogDao.deleteJog(jog)


    fun getAllRunsSortedByDate() = jogDao.getAllJogSortedByDate()

    fun getAllRunsSortedByDistance() = jogDao.getAllJogSortedByDistance()

    fun getAllRunsSortedByTimeInMillis() = jogDao.getAllJogSortedByTimInMillis()

    fun getAllRunsSortedByAvgSpeed() = jogDao.getAllJogSortedByAvgSpeed()

    fun getAllRunsSortedByCaloriesBurned() = jogDao.getAllJogSortedByCaloriesBurned()

    fun getTotalAvgSpeed() = jogDao.getTotalAvgSpeed()

    fun getTotalDistance() = jogDao.getTotalDistanceInMeters()

    fun getTotalCaloriesBurned() = jogDao.getTotalCaloriesBurned()

    fun getTotalTimeInMillis() = jogDao.getTotalTimeInMillis()
}