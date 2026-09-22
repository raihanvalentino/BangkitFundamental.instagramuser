package com.example.submissionawal.ui

import android.app.Application
import com.example.submissionawal.dataClass.DetailGithubUser
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submissionawal.API.RetrofitDec
import com.example.submissionawal.dbfav.DatabaseUser
import com.example.submissionawal.dbfav.FavUsers
import com.example.submissionawal.dbfav.FavUsersDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewDetailUser(application: Application) : AndroidViewModel(application) {

    val listDetailUserGithub = MutableLiveData<DetailGithubUser>()
    val loading = MutableLiveData<Boolean>()

    private  var usersDao : FavUsersDao?
    private var db : DatabaseUser?

    init {
        db = DatabaseUser.getDb(application)
        usersDao = db?.userFavDao()
    }

    fun setDetailGithubUser(username: String) {
        loading.value = true

        RetrofitDec.api
            .getDetailUser(username)
            .enqueue(object : Callback<DetailGithubUser> {
                override fun onResponse(
                    call: Call<DetailGithubUser>,
                    response: Response<DetailGithubUser>
                ) {
                    if (response.isSuccessful) {
                        listDetailUserGithub.postValue(response.body())
                    }
                    loading.value = false
                }

                override fun onFailure(call: Call<DetailGithubUser>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }
    fun getCariDetailUser(): LiveData<DetailGithubUser> {
        return listDetailUserGithub
    }

    fun getLoadingState(): LiveData<Boolean> {
        return loading
    }
    fun add (login:String , id:Int , avatar_url : String){
        CoroutineScope(Dispatchers.IO).launch {
            val userGithubFav = FavUsers(
                login,
                id,
                avatar_url
            )
            usersDao?.addToFavorite(userGithubFav)
        }
    }

    suspend fun check(id: Int?) = usersDao?.checkFavoriteUser(id!!)

    fun remove (id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            usersDao?.removeFromFav(id)
        }
    }
}