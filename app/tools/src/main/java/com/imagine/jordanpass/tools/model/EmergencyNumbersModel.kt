package com.imagine.jordanpass.tools.model

import com.google.gson.annotations.SerializedName

data class EmergencyNumbersModel(@SerializedName("id") val id:Int,@SerializedName("name") val name:String,@SerializedName("phoneNo") val phoneNo:String)