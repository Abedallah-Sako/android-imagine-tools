package com.imagine.jordanpass.tools.model.story


import com.google.gson.annotations.SerializedName

data class uploadResponse(
    @SerializedName("StoryId")
    val storyId: Int?
)