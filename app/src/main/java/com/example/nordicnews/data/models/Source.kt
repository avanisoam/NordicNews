package com.example.nordicnews.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    val id: String?,
    val name: String
    // TODO: Handle null
    //val name: String?
) : Parcelable
