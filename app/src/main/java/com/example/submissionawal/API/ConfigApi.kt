package com.example.submissionawal.API

import com.example.submissionawal.dataClass.DetailGithubUser
import com.example.submissionawal.dataClass.GithubUser
import com.example.submissionawal.dataClass.GithubUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ConfigApi {
	@GET("search/users")
	fun getCariUser(
		@Query("q") query : String
	): Call<GithubUserResponse>

	@GET("users/{username}")
	fun getDetailUser(
		@Path("username") username : String
	): Call<DetailGithubUser>

	@GET("users/{username}/followers")
	fun getCariDetailUserFollowers(
		@Path("username") username: String
	): Call<ArrayList<GithubUser>>

	@GET("users/{username}/following")
	fun getCariDetailUserFollowing(
		@Path("username") username: String
	): Call<ArrayList<GithubUser>>
}