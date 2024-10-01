package com.imagine.jordanpass.tools.model


import com.google.gson.annotations.SerializedName

data class PDFGenerationBody(
    @SerializedName("Events")
    val events: List<Event?>?,
    @SerializedName("UserFullName")
    val userFullName: String?, // Rawwagah
    @SerializedName("UserPhoneNumber")
    val userPhoneNumber: String? // 0000
) {
    data class Event(
        @SerializedName("Description")
        val description: String?, // Amman offers mild weather perfect for exploring the Citadel and Roman Theatre.
        @SerializedName("EventName")
        val eventName: String?, // Ceramics & Pottery Making
        @SerializedName("Experiences")
        val experiences: List<Experience?>?,
        @SerializedName("Image")
        val image: String?, // https://jordangate.imagine.com.jo/Uploaded/LocationImages/6.jpg
        @SerializedName("Tips")
        val tips: List<String?>?,
        @SerializedName("TransportationLink")
        val transportationLink: String? // https://maps.app.goo.gl/vHEdUqBhWoAiPjnJ9
    ) {
        data class Experience(
            @SerializedName("Duration")
            val duration: String?, // 2 hours
            @SerializedName("Image")
            val image: String?, // https://plus.unsplash.com/premium_photo-1673254928968-b6513f32d43b?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D
            @SerializedName("MapLocationLink")
            val mapLocationLink: String?, // https://maps.app.goo.gl/vHEdUqBhWoAiPjnJ9
            @SerializedName("Title")
            val title: String? // Workshop Introduction
        )
    }
}