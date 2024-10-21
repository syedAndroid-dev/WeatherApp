package com.example.weatherapp.core.appdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.core.utils.Constants.USER_DETAILS
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetails(user: UserDetails)

    @Query("DELETE FROM $USER_DETAILS WHERE userId = :userId")
    suspend fun deleteUserDetails(userId  : Int)

    @Query("SELECT * FROM $USER_DETAILS")
    fun getUserDetails(): Flow<List<UserDetails>>
}