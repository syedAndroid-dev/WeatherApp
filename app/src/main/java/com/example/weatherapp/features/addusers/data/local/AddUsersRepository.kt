package com.example.weatherapp.features.addusers.data.local

import com.example.weatherapp.core.appdb.UserDetails
import com.example.weatherapp.core.appdb.UserDetailsDao
import javax.inject.Inject

class AddUsersRepository @Inject constructor(
    private val userDetailsDao: UserDetailsDao
) {

    suspend fun addUser(userDetails: UserDetails){
        userDetailsDao.insertUserDetails(user = userDetails)
    }
}