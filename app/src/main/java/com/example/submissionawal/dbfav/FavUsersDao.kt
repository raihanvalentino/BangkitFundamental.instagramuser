package com.example.submissionawal.dbfav

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavUsersDao {
    @Insert
    suspend fun  addToFavorite(favUsers: FavUsers)

    @Query ("SELECT count (*) FROM favorite_users WHERE favorite_users.id = :id")
    suspend fun checkFavoriteUser(id:Int) : Int


    @Query("DELETE FROM favorite_users WHERE favorite_users.id = :id")
    suspend fun removeFromFav(id: Int): Int

    @Query("SELECT * FROM favorite_users ")
    fun getFavoriteUsers ():LiveData<List<FavUsers>>


}