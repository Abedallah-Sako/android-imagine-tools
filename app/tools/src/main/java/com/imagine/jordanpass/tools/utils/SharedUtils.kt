package com.imagine.jordanpass.tools.utils

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedUtils @Inject constructor(private val sharedPreferences: SharedPreferences) {

    //"lat,lng"
    fun getSavedLocation(key:String): String {
        return sharedPreferences.getString(key,"")?:""
    }

    //"lat,lng"
    fun setSavedLocation(key:String,location: String) {
        with(sharedPreferences.edit()) {
            putString(key, location)
            apply()
        }
    }


    fun getLocationRequestDate(key:String): Long {
        return sharedPreferences.getLong(key,0L)
    }

    fun setLocationRequestDate(key:String,date: Long) {
        with(sharedPreferences.edit()) {
            putLong(key, date)
            apply()
        }
    }

    fun setOrderId(key:String,orderId:String){
        with(sharedPreferences.edit()){
            putString(key,orderId)
            apply()
        }
    }

    fun getOrderId(key: String):String{
        return sharedPreferences.getString(key,"")?:""
    }

    fun setInterests(key: String, interests:String){
        with(sharedPreferences.edit()){
            putString(key,interests)
            apply()
        }
    }

    fun getInterests(key: String):String?{
        return sharedPreferences.getString(key,null)
    }

    fun setInterestsName(key: String, name:String){
        with(sharedPreferences.edit()){
            putString(key,name)
            apply()
        }
    }

    fun getInterestsName(key: String):String?{
        return sharedPreferences.getString(key,null)
    }


    fun setOnBoardingDone(key:String){
        with(sharedPreferences.edit()){
            putBoolean(key,true)
            apply()
        }
    }

    fun isOnBoardingDone(key:String):Boolean{
        return sharedPreferences.getBoolean(key,false)
    }


    fun setFirebaseToken(key:String,token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, token)
        editor.apply()
    }

    fun setFirebaseTokenUpdated(key:String,isUpdated: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, isUpdated)
        editor.apply()
    }

    fun isFirebaseTokenUpdated(key:String) =
        sharedPreferences.getBoolean(key, false) ?: false

    fun getFirebaseToken(key:String) = sharedPreferences.getString(key, "") ?: ""

}