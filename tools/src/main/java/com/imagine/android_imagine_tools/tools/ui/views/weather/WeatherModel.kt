package com.imagine.android_imagine_tools.tools.ui.views.weather


import com.google.gson.annotations.SerializedName

class WeatherModel : ArrayList<WeatherModel.WeatherModelItem>() {

    data class WeatherModelItem(
        @SerializedName("cityName")
        val cityName: String,
        @SerializedName("cityNameAr")
        val cityNameAr: String,
        @SerializedName("day")
        val day: String,
        @SerializedName("day_time")
        val dayTime: DayTime,
        @SerializedName("night_time")
        val nightTime: NightTime
    ) {

        data class DayTime(
            @SerializedName("ends")
            val ends: Long,
            @SerializedName("image")
            val image: Any,
            @SerializedName("pressure")
            val pressure: Pressure,
            @SerializedName("relative_humidity")
            val relativeHumidity: RelativeHumidity,
            @SerializedName("starts")
            val starts: Long,
            @SerializedName("status")
            val status: Status,
            @SerializedName("temperature")
            val temperature: Temperature,
            @SerializedName("wind_speed")
            val windSpeed: WindSpeed
        ) {


            data class Pressure(
                @SerializedName("max")
                val max: Double,
                @SerializedName("min")
                val min: Double
            )


            data class RelativeHumidity(
                @SerializedName("max")
                val max: Double,
                @SerializedName("min")
                val min: Double
            )


            data class Status(
                @SerializedName("arabic_desc")
                val arabicDesc: String,
                @SerializedName("english_desc")
                val englishDesc: String,
                @SerializedName("id")
                val id: String
            )


            data class Temperature(
                @SerializedName("max")
                val max: Int,
                @SerializedName("min")
                val min: Int
            )


            data class WindSpeed(
                @SerializedName("max")
                val max: Double,
                @SerializedName("min")
                val min: Double
            )
        }


        data class NightTime(
            @SerializedName("ends")
            val ends: Long,
            @SerializedName("image")
            val image: Any,
            @SerializedName("pressure")
            val pressure: Pressure,
            @SerializedName("relative_humidity")
            val relativeHumidity: RelativeHumidity,
            @SerializedName("starts")
            val starts: Long,
            @SerializedName("status")
            val status: Status,
            @SerializedName("temperature")
            val temperature: Temperature,
            @SerializedName("wind_speed")
            val windSpeed: WindSpeed
        ) {


            data class Pressure(
                @SerializedName("max")
                val max: Double,
                @SerializedName("min")
                val min: Double
            )


            data class RelativeHumidity(
                @SerializedName("max")
                val max: Double,
                @SerializedName("min")
                val min: Double
            )


            data class Status(
                @SerializedName("arabic_desc")
                val arabicDesc: String,
                @SerializedName("english_desc")
                val englishDesc: String,
                @SerializedName("id")
                val id: String
            )


            data class Temperature(
                @SerializedName("max")
                val max: Int,
                @SerializedName("min")
                val min: Int
            )


            data class WindSpeed(
                @SerializedName("max")
                val max: Double,
                @SerializedName("min")
                val min: Double
            )
        }
    }
}