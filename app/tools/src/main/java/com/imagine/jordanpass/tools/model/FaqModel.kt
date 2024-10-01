package com.imagine.jordanpass.tools.model

import com.google.gson.annotations.SerializedName

data class FaqModel(@SerializedName("id") val id:Int,
                    @SerializedName("question") val question:String,
                    @SerializedName("answer") val answer:String,
                    var expanded:Boolean = false)