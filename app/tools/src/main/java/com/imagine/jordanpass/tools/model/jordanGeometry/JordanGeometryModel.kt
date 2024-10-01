package com.imagine.jordanpass.tools.model.jordanGeometry

import com.google.gson.annotations.SerializedName

data class JordanGeometryModel(
    @SerializedName("features")
    val features: List<Feature>,
    @SerializedName("type")
    val type: String
) {
    data class Feature(
        @SerializedName("geometry")
        val geometry: Geometry,
        @SerializedName("properties")
        val properties: Properties,
        @SerializedName("type")
        val type: String
    ) {
        data class Geometry(
            @SerializedName("coordinates")
            val coordinates: List<List<List<Double>>>,
            @SerializedName("type")
            val type: String
        )

        data class Properties(
            @SerializedName("shapeGroup")
            val shapeGroup: String,
            @SerializedName("shapeID")
            val shapeID: String,
            @SerializedName("shapeISO")
            val shapeISO: String,
            @SerializedName("shapeName")
            val shapeName: String,
            @SerializedName("shapeType")
            val shapeType: String
        )
    }
}