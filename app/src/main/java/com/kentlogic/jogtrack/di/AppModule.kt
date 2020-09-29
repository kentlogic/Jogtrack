package com.kentlogic.jogtrack.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.kentlogic.jogtrack.db.JogDatabase
import com.kentlogic.jogtrack.util.Constants.JOGGING_DATABASE_NAME
import com.kentlogic.jogtrack.util.Constants.KEY_FIRST_TIME_TOGGLE
import com.kentlogic.jogtrack.util.Constants.KEY_NAME
import com.kentlogic.jogtrack.util.Constants.KEY_WEIGHT
import com.kentlogic.jogtrack.util.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) //Module will live as long as the component lives
object AppModule {
    //Dagger Hilt will use the application context, ensure that only one instance is running
    @Singleton
    @Provides
    fun provideJoggingDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        JogDatabase::class.java,
        JOGGING_DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideJogDao(db: JogDatabase) = db.getJogDao()


    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)


    @Singleton
    @Provides
    fun providesName(sharedPref: SharedPreferences) = sharedPref.getString(KEY_NAME, "")
        ?: "" // To address kotlin issue, sharedpref in kotlin returns null even if "" is set

    @Singleton
    @Provides
    fun providesWeight(sharedPref: SharedPreferences) = sharedPref.getFloat(KEY_WEIGHT, 65f)


    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) = sharedPref.getBoolean(
        KEY_FIRST_TIME_TOGGLE, true)

}
