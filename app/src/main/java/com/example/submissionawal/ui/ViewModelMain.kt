package com.example.submissionawal.ui
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawal.API.RetrofitDec
import com.example.submissionawal.dataClass.GithubUser
import com.example.submissionawal.dataClass.GithubUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList



class ViewModelMain : ViewModel() {
    val listUserGithub = MutableLiveData<ArrayList<GithubUser>>()
    val loading = MutableLiveData<Boolean>()

    fun setGithubUser(namaorang : String){
        loading.value =true
        RetrofitDec.api
            .getCariUser(namaorang)
            .enqueue(object : Callback<GithubUserResponse>{
                override fun onResponse(
                    call: Call<GithubUserResponse>,
                    response: Response<GithubUserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUserGithub.postValue(response.body()?.items)

                    }
                    loading.value =false
                }

                override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }
    fun getCariGithubUser() : LiveData<ArrayList<GithubUser>>{
        return listUserGithub
    }
    fun getLoadingState() : LiveData<Boolean>{
        return  loading
    }


}