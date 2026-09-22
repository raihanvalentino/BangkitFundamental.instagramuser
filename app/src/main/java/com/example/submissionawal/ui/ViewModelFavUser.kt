package com.example.submissionawal.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submissionawal.dbfav.DatabaseUser
import com.example.submissionawal.dbfav.FavUsers
import com.example.submissionawal.dbfav.FavUsersDao

class ViewModelFavUser (application: Application) : AndroidViewModel(application){

    private  var usersDao : FavUsersDao?
    private var db : DatabaseUser?

    init {
        db = DatabaseUser.getDb(application)
        usersDao = db?.userFavDao()
    }
    fun getFavoriteUsers() : LiveData<List<FavUsers>>?{
        return usersDao?.getFavoriteUsers()
    }
}