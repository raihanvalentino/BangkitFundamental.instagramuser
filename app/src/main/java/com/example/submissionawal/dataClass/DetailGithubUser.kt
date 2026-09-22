package com.example.submissionawal.dataClass

data class DetailGithubUser(
    val name : String,
    val login : String,
    val company : String,
    val location : String,
    val bio : String,
    val followers : Int,
    val followers_url : String,
    val following : String,
    val following_url: String,
    val public_repos : String,
    val avatar_url : String
)