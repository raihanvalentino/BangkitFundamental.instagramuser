package com.example.submissionawal.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawal.API.RetrofitDec
import com.example.submissionawal.dataClass.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelFollowers : ViewModel() {
    val listUserGithubFollowers = MutableLiveData<ArrayList<GithubUser>>()
    val loading = MutableLiveData<Boolean>()

    fun setUserFollowers(dataFollowers: String) {
        loading.value =true
        RetrofitDec.api
            .getCariDetailUserFollowers(dataFollowers)
            .enqueue(object : Callback<ArrayList<GithubUser>> {
                override fun onResponse(
                    call: Call<ArrayList<GithubUser>>,
                    response: Response<ArrayList<GithubUser>>
                ) {
                    if (response.isSuccessful) {
                        listUserGithubFollowers.postValue(response.body())

                    }
                    loading.value = false
                }

                override fun onFailure(call: Call<ArrayList<GithubUser>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getCariFollowersUser(): LiveData<ArrayList<GithubUser>> {
        return listUserGithubFollowers
    }

    fun getLoadingState(): LiveData<Boolean> {
        return loading

    }
}