package com.imagine.jordanpass.tools.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalExperience(
    @SerializedName("Category")
    val category: String?,
    @SerializedName("City")
    val city: String?, // Petra
    @SerializedName("Description")
    val description: String?, // Welcome to Petra, a UNESCO World Heritage site and one of the New Seven Wonders of the World. Known as the Rose City due to the color of the stone from which it is carved, Petra is a breathtaking archaeological marvel nestled in the rugged desert canyons of southern Jordan.  Uncover the Lost City  Begin your Petra adventure by walking through the narrow, winding gorge known as the Siq, surrounded by towering cliffs that gradually reveal the iconic Treasury building. Marvel at the intricate façade and intricate carvings that showcase the architectural prowess of the ancient Nabateans.  Explore the Ancient City  Petra is not just about the Treasury the site is vast and holds numerous other wonders. Spend your day exploring the ancient amphitheater, the Monastery with its 800-step climb for panoramic views, and the Royal Tombs that showcase the rich history and craftsmanship of this ancient civilization.  Hiking Trails for the Adventurous  For those seeking a bit of adventure, Petra offers various hiking trails leading to lesser-known but equally captivating sites. The High Place of Sacrifice and the Petra Church are just a couple of examples. These trails not only provide unique perspectives of Petra but also immerse you in the raw beauty of the surrounding landscape.  Petra by Night  Experience Petra in a different light—literally. Petra by Night is a magical experience where the entire site is illuminated by countless candles, creating an enchanting atmosphere. As you follow the candle-lit path through the Siq to the Treasury, youll feel transported to another time, surrounded by the mysteries of this ancient city.
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("Family")
    val family: Int?, // 0
    @SerializedName("Grade")
    val grade: String?,
    @SerializedName("GroupSize")
    val groupSize: String?,
    @SerializedName("Id")
    val id: Int?, // 17
    @SerializedName("Image")
    val image: String?, // https://jordangate.imagine.com.jo/Uploaded/LocalRecommendations%20Images/17.jpg
    @SerializedName("KeyGuid")
    val keyGuid: String?, // null
    @SerializedName("Thumbnail")
    val thumbnail: String?, // null
    @SerializedName("Title")
    val title: String? // Ancient Wonders & Rose-Colored Marvels
):Parcelable