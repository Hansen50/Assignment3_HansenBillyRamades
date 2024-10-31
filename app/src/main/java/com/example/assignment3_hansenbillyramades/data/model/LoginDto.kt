package com.example.assignment3_hansenbillyramades.data.model

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("firstName")
        val firstName: String,

        @SerializedName("lastName")
        val lastName: String,

        @SerializedName("email")
        val email: String,

        @SerializedName("phone")
        val phone: String,

        @SerializedName("avatar")
        val avatar: String,

        @SerializedName("token")
        val token: String
    )
}
