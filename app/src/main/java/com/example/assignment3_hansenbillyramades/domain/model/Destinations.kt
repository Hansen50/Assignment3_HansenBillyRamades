package com.example.assignment3_hansenbillyramades.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destinations(
    val id: Int,
    val name: String,
    val description: String,
    val duration: String,
    val image: String,
    val location: String,
    val activity: String,
    val popularity: String,
    val type: String,
) : Parcelable