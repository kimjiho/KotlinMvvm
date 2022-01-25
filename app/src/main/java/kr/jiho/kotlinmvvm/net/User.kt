package kr.jiho.kotlinmvvm.net

import com.google.gson.annotations.SerializedName

class UserList(
    @SerializedName("data")
    val data: ArrayList<User>
)

class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("status")
    val status: String,
)