package com.kentlogic.jogtrack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Jog::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class JogDatabase: RoomDatabase (){

    abstract fun getJogDao(): JogDao
}