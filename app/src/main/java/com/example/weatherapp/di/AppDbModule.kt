package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.core.appdb.UserDetailsDao
import com.example.weatherapp.core.appdb.UserDetailsDatabase
import com.example.weatherapp.core.utils.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDbModule{

    @Provides
    @Singleton
    fun providesUserDatabase(
        @ApplicationContext context : Context
    ) : UserDetailsDatabase{
        return Room.databaseBuilder(
            context = context,
            UserDetailsDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesUserDatabaseDto(db : UserDetailsDatabase) : UserDetailsDao = db.getUserDetailsDto()

}