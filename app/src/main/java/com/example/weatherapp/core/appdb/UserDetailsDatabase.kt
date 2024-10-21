package com.example.weatherapp.core.appdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserDetails::class],
    version = 1,
    exportSchema = true
)
abstract class UserDetailsDatabase : RoomDatabase() {
    abstract fun getUserDetailsDto() : UserDetailsDao
}