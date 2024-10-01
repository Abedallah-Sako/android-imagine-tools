package com.imagine.jordanpass.tools.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class OffersModel(
    @SerializedName("Steps")
    val steps: List<Step?>?
) {

    @Parcelize
    data class Step(
        @SerializedName("Category")
        val category: List<Int?>?,
        @SerializedName("CatigoriesString")
        val catigoriesString: String?, // null
        @SerializedName("City")
        val city: String?, // C
        @SerializedName("EndDate")
        val endDate: String?, // 1/31/2024 12:00:00 AM
        @SerializedName("Id")
        val id: Int?, // 2
        @SerializedName("Image")
        val image: String?, // https://jordangate.imagine.com.jo/Uploaded/Offer%20Images/2.jpg
        @SerializedName("OfferLink")
        val offerLink: String?, // O
        @SerializedName("StartDate")
        val startDate: String?, // 1/30/2024 12:00:00 AM
        @SerializedName("Title")
        val title: String? // T
    ):Parcelable
}