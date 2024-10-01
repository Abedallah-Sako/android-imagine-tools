package com.imagine.jordanpass.tools.model


import com.google.gson.annotations.SerializedName

data class InterestsModel(
    @SerializedName("icon")
    val icon: String?, // www.example.com/icons/21654.png
    @SerializedName("id")
    val id: Int?, // 1
    @SerializedName("name")
    val name: String?, // Sightseeing
    @SerializedName("isChecked")
    var isChecked:Boolean = false
)