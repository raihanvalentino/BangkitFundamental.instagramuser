package com.example.submissionawal.dbfav

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity (tableName = "favorite_users")
data class FavUsers(
    val login : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val avatar_url : String
):Serializable
