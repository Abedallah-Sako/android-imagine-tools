package com.imagine.jordanpass.tools.network.data

import android.accounts.NetworkErrorException
import android.util.Log
import com.imagine.jordanpass.tools.ext.logd
import com.imagine.jordanpass.tools.model.AttractionsModel
import com.imagine.jordanpass.tools.repositories.ApiRepository
import retrofit2.HttpException
import java.io.IOException

object AttractionsData {

    private var attractions: AttractionsModel? = null

    suspend fun getAttractions(apiRepository: ApiRepository,url:String, refresh:Boolean = false): List<AttractionsModel.AttractionsModelItem>? {

        if (attractions == null || refresh) {

            logd("GettingAroundData")
            val error: String? = try {
                val response = apiRepository.getAttractions(url)
                if (response.body() == null) {
                    Log.e("GettingAroundData", "getCategories: ${response.errorBody()}")
                }else if(!response.isSuccessful){
                    Log.e("GettingAroundData", "getCategories: ${response.message()}")
                }
                else if (response.isSuccessful) {
                    attractions = response.body()
//                    attractions?.size?.logd("attractions")
                }
                null
            } catch (e: NetworkErrorException) {
                e.printStackTrace()
                e.message
            } catch (e: HttpException) {
                e.printStackTrace()
                e.message
            } catch (e: IOException) {
                e.printStackTrace()
                e.message
            } catch (e: Throwable) {
                e.printStackTrace()
                e.message
            }

            //deep copy
            val temp = mutableListOf<AttractionsModel.AttractionsModelItem>()
            attractions?.forEach() { temp.add(it.copy()) }
//            attractions
            return temp
        } else {
            //deep copy
            val temp = mutableListOf<AttractionsModel.AttractionsModelItem>()
            attractions?.forEach() { temp.add(it.copy()) }
            return temp
        }

    }
}
