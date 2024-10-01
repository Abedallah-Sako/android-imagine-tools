package com.imagine.jordanpass.tools.model

import com.google.gson.annotations.SerializedName

data class BaseModel<T> (@SerializedName("data")
                             val data: T,
                         @SerializedName("errorMessage")
                             val errorMessage: String?,
                         @SerializedName("status")
                             val status: Boolean?)