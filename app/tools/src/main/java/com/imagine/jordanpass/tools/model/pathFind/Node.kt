package com.imagine.jordanpass.tools.model.pathFind

import android.location.Location
import com.imagine.jordanpass.tools.model.ammanBus.AmmanBusRouteModel

data class Node(val ammanBusRouteModel: AmmanBusRouteModel.AmmanBusRouteModelItem?, var g:Double, var h:Double, var hStop: AmmanBusRouteModel.AmmanBusRouteModelItem.BusStop?, var gStop: AmmanBusRouteModel.AmmanBusRouteModelItem.BusStop?, var prevNode: Node?){

    fun getNeighborRoutes(ammanBusRoutes:List<AmmanBusRouteModel.AmmanBusRouteModelItem>, distanceMeters:Int, start: Pair<Double, Double>, end: Pair<Double, Double>):List<Node>{
        val neighborsRoutes = mutableListOf<AmmanBusRouteModel.AmmanBusRouteModelItem>()
        val result: FloatArray = floatArrayOf(0.0f)


        //find all close routes
        ammanBusRoutes.forEach {
            if(it != ammanBusRouteModel){
                var min = Float.MAX_VALUE

                it.busStopList?.forEach { busStop ->
                    Location.distanceBetween(hStop?.lat?.toDouble()?:0.0,hStop?.lng?.toDouble()?:0.0,busStop?.lat?.toDouble()?:0.0,busStop?.lng?.toDouble()?:0.0,result)
                    if(result[0] < min){
                        min = result[0]
                    }

                }

                if(min < distanceMeters){
                        neighborsRoutes.add(it)
                }
            }
        }


        val neighborNodes = mutableListOf<Node>()

        //creating nodes
        neighborsRoutes.forEach {
            var minG = Float.MAX_VALUE
            var minH = Float.MAX_VALUE
            var minHBusStop : AmmanBusRouteModel.AmmanBusRouteModelItem.BusStop? = null
            var minGBusStop : AmmanBusRouteModel.AmmanBusRouteModelItem.BusStop? = null


            it.busStopList?.forEach { busStop ->

                //find h
                Location.distanceBetween(busStop?.lat?.toDouble()?:0.0,busStop?.lng?.toDouble()?:0.0,end.first,end.second,result)

                if (result[0] < minH){
                    minH = result[0]
                    minHBusStop = busStop
                }

                //find g
                Location.distanceBetween(busStop?.lat?.toDouble()?:0.0,busStop?.lng?.toDouble()?:0.0,start.first,start.second,result)
                if (result[0] < minG){
                    minG = result[0]
                    minGBusStop = busStop
                }

            }


            neighborNodes.add(Node(it,minG.toDouble() + g,minH.toDouble(),minHBusStop,minGBusStop,this))
        }


        return neighborNodes
    }

    fun getF():Double{
        return g+h
    }


}