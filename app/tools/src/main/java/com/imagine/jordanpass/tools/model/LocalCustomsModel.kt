package com.imagine.jordanpass.tools.model


import com.google.gson.annotations.SerializedName

data class LocalCustomsModel(
    @SerializedName("do")
    val doX: List<Do?>?,
    @SerializedName("dont")
    val dont: List<Dont?>?
) {
    data class Do(
        @SerializedName("id")
        val id: Int?, // 9
        @SerializedName("custom")
        val custom: String? // Do shake hands when meeting people; conservative veiled women may not reach out.
    )

    data class Dont(
        @SerializedName("id")
        val id: Int?, // 19
        @SerializedName("custom")
        val custom: String? // Don't interrupt, or pass in front of, a Muslim who may be praying in a public place.
    )
}