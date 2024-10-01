package com.imagine.jordanpass.tools.model.ammanBus


import com.google.gson.annotations.SerializedName

class AmmanBusRouteModel : ArrayList<AmmanBusRouteModel.AmmanBusRouteModelItem>(){
    data class AmmanBusRouteModelItem(
        @SerializedName("busStopList")
        val busStopList: List<BusStop?>?,
        @SerializedName("displayRouteCode")
        val displayRouteCode: String?,
        @SerializedName("headSign")
        val headSign: String?,
        @SerializedName("path_code")
        val pathCode: String?,
        @SerializedName("pointList")
        val pointList: List<Point?>?,
        @SerializedName("routeColor")
        val routeColor: String?,
        @SerializedName("tripShortName")
        val tripShortName: String?
    ) {
        data class BusStop(
            @SerializedName("arrival_offset")
            val arrivalOffset: String?,
            @SerializedName("busOnStop")
            val busOnStop: String?,
            @SerializedName("departure_offset")
            val departureOffset: String?,
            @SerializedName("dropoffType")
            val dropoffType: String?,
            @SerializedName("lat")
            val lat: String?,
            @SerializedName("lng")
            val lng: String?,
            @SerializedName("pickupType")
            val pickupType: String?,
            @SerializedName("routeColor")
            val routeColor: String?,
            @SerializedName("routeTextColor")
            val routeTextColor: String?,
            @SerializedName("routeType")
            val routeType: String?,
            @SerializedName("routes")
            val routes: String?,
            @SerializedName("seq")
            val seq: String?,
            @SerializedName("stopId")
            val stopId: String?,
            @SerializedName("stopName")
            val stopName: String?,
            @SerializedName("timeTable")
            val timeTable: String?
        ){

            companion object{
                fun createEmptyBusStopFromLatLng(lat:Double,lng:Double): BusStop {
                    return BusStop(null,null,null,null,lat.toString(),lng.toString(),null,null,null,null,null,null,null,null,null)
                }
            }

        }
    
        data class Point(
            @SerializedName("lat")
            val lat: String?,
            @SerializedName("lng")
            val lng: String?,
            @SerializedName("seq")
            val seq: String?
        )
    }
}