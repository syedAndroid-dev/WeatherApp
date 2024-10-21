package com.example.weatherapp.features.home.data.local

import com.example.weatherapp.core.appdb.UserDetails
import com.example.weatherapp.core.appdb.UserDetailsDao
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val userDetailsDao: UserDetailsDao
) {

    fun getUserDetails() = userDetailsDao.getUserDetails()

    suspend fun delete(userId:Int) = userDetailsDao.deleteUserDetails(userId)
}