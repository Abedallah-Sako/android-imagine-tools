package com.imagine.jordanpass.tools.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalRecommendationModel(
    @SerializedName("Id") val id: Int?,
    @SerializedName("Title") val title: String?,
    @SerializedName("Description") val description: String?,
    @SerializedName("Image") val image: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("City") val city: String?

):Parcelable