package com.imagine.jordanpass.tools.model


import com.google.gson.annotations.SerializedName

data class MuseumInnerModel(
    @SerializedName("City")
    val city: String?, // Petra
    @SerializedName("description")
    val description: String?, // <p>The Petra Museum contains 280 artifacts, dating back to different ages. The exhibition consists of five halls showing the history of Petra, information about the Nabataea’s way of life, their civilization.<br></p>
    @SerializedName("governorate")
    val governorate: String?, // Petra
    @SerializedName("id")
    val id: Int?, // 2
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("latlong")
    val latlong: String?, // 30.325344105989654, 35.46800799999797
    @SerializedName("openingHours")
    val openingHours: String?, // <h4>Summer<span>8:30 AM - 10:00 PM</span></h4><h4>Winter<span>8:30 AM - 8:30 PM</span></h4>
    @SerializedName("phone")
    val phone: String?, // 03-2156044
    @SerializedName("thumbnail")
    val thumbnail: String?, // https://museums.visitjordan.com/uploads/museums/e43e6e1a-e577-4592-a2b2-84f92107f1dd.png
    @SerializedName("title")
    val title: String? // The Petra Museum
)