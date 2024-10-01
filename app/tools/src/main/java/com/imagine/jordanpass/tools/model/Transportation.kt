package com.imagine.jordanpass.tools.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transportation(
    @SerializedName("Id")
    val id: Int?, // 2
    @SerializedName("Sections")
    val sections: List<Section>?,
    @SerializedName("Title")
    val title: String? // Petra Ride
) : Parcelable {
    @Parcelize
    data class Section(
        @SerializedName("Description")
        val description: String?, // Text test
        @SerializedName("Image")
        val image: String?, // https://jordangate.imagine.com.jo/uploaded/trans/8db911b0-fc57-4bff-b2fa-24f9ec7a5cb5.png
        @SerializedName("Title")
        val title: String? // title test
    ) : Parcelable
}