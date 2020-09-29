package com.kentlogic.jogtrack.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface JogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJog(jog: Jog)

    @Delete
    suspend fun deleteJog(jog: Jog)

    @Query("SELECT * FROM jogging_table ORDER BY timestamp DESC")
    fun getAllJogSortedByDate(): LiveData<List<Jog>>

    @Query("SELECT * FROM jogging_table ORDER BY timeInMillis DESC")
    fun getAllJogSortedByTimInMillis(): LiveData<List<Jog>>

    @Query("SELECT * FROM jogging_table ORDER BY caloriesBurned DESC")
    fun getAllJogSortedByCaloriesBurned(): LiveData<List<Jog>>

    @Query("SELECT * FROM jogging_table ORDER BY avgSpeedKmh DESC")
    fun getAllJogSortedByAvgSpeed(): LiveData<List<Jog>>

    @Query("SELECT * FROM jogging_table ORDER BY distanceInMeters DESC")
    fun getAllJogSortedByDistance(): LiveData<List<Jog>>

    @Query("SELECT SUM(timeInMillis) from jogging_table")
    fun getTotalTimeInMillis(): LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) from jogging_table")
    fun getTotalCaloriesBurned(): LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) from jogging_table")
    fun getTotalDistanceInMeters(): LiveData<Int>

    @Query("SELECT AVG(avgSpeedKmh) from jogging_table")
    fun getTotalAvgSpeed(): LiveData<Float>
}