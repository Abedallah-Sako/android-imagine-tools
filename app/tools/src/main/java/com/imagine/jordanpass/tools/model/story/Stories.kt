package com.imagine.jordanpass.tools.model.story


import com.google.gson.annotations.SerializedName

    data class Stories(
        @SerializedName("CategoryOrder")
        val categoryOrder: Int?, // 1
        @SerializedName("Id")
        val id: Int?, // 2
        @SerializedName("Stories")
        val stories: List<Story?>?,
        @SerializedName("Thumbnail")
        val thumbnail: String?, // .jpg
        @SerializedName("Title")
        val title: String? // California
    ) {
        data class Story(
            @SerializedName("Date")
            val date: String?, // 2023-12-17T13:30:43
            @SerializedName("Description")
            val description: String?, // Test Description
            @SerializedName("Id")
            val id: Int?, // 6
            @SerializedName("title")
            val title: String?, // Story Name
            @SerializedName("url")
            val url: String? // https://jordangate.imagine.com.jo/Uploaded/Story/6.jpg
        )
    }