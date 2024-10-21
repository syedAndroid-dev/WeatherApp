package com.example.weatherapp.core.appdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.core.utils.Constants.USER_DETAILS
import com.example.weatherapp.core.utils.Constants.USER_EMAIL
import com.example.weatherapp.core.utils.Constants.USER_NAME

@Entity(tableName = USER_DETAILS)
data class UserDetails(
    @PrimaryKey(autoGenerate = true)
    val userId : Int? = null,

    @ColumnInfo(name = USER_NAME)
    val userName : String,

    @ColumnInfo(name = USER_EMAIL)
    val userEmail : String,
)