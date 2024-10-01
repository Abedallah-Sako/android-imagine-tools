package com.imagine.jordanpass.tools.model.landMark

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LandMarkModel(
    @SerializedName("brt")
    val brt: Int?,
    @SerializedName("busSpeed")
    val busSpeed: Double?,
    @SerializedName("carSpeed")
    val carSpeed: Double?,
    @SerializedName("category")
    val category: List<Int?>?,
    @SerializedName("comfortLevel")
    val comfortLevel: String?,
    @SerializedName("distance")
    val distance: String?,
    @SerializedName("distanceInMeters")
    val distanceInMeters: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longtitude")
    val longtitude: String?,
    @SerializedName("shareData")
    val shareData: String?,
    @SerializedName("street")
    val street: String?,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("time")
    val time: Time?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("transportationTypes")
    val transportationTypes: Int?,
    @SerializedName("walkingSpeed")
    val walkingSpeed: Double?,
    var isFavorites: Boolean = false
) : Parcelable {


    @Parcelize
    data class Time(
        @SerializedName("bus")
        val bus: Double?,
        @SerializedName("car")
        val car: Double?,
        @SerializedName("walking")
        val walking: Double?
    ) : Parcelable {
    }

}


