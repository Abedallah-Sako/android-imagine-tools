package com.imagine.jordanpass.tools.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MuseumsModel(
    @SerializedName("City")
    val city: String?, // Petra
    @SerializedName("id")
    val id: Int?, // 2
    @SerializedName("thumbnail")
    val thumbnail: String?, // https://museums.visitjordan.com/uploads/museums/e43e6e1a-e577-4592-a2b2-84f92107f1dd.png
    @SerializedName("title")
    val title: String? // The Petra Museum
):Parcelable {
}