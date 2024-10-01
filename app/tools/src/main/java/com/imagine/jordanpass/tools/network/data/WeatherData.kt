package com.imagine.jordanpass.tools.network.data

import android.accounts.NetworkErrorException
import android.util.Log
import com.imagine.jordanpass.tools.ext.handleErrors
import com.imagine.jordanpass.tools.model.BaseModel
import com.imagine.jordanpass.tools.repositories.ApiRepository
import com.imagine.jordanpass.tools.utils.apiHandleResponse.HandleResults
import com.imagine.jordanpass.tools.utils.apiHandleResponse.Resource
import com.imagine.jordanpass.tools.views.weather.WeatherModel
import retrofit2.HttpException
import java.io.IOException

object WeatherData {
    private var weather: Resource<BaseModel<WeatherModel>>? = null

    suspend fun getWeather(apiRepository: ApiRepository,url:String, lat:Double, Lng:Double): Resource<List<WeatherModel.WeatherModelItem>>? {

        if (weather == null) {
            weather = (Resource.Loading())

            val error: String? = try {
                val response = apiRepository.getWeather(url,lat,Lng)
                if (response.body() == null) {
                    weather = Resource.Error(handleErrors("Empty response"))
                    Log.e("GettingAroundData", "getCategories: ${response.errorBody()}")
                }else if(!response.isSuccessful){
                    weather = Resource.Error(handleErrors(response.message()))
                    Log.e("GettingAroundData", "getCategories: ${response.message()}")
                }
                else{
                    weather =  HandleResults<BaseModel<WeatherModel>>().handle(response)
//                    weather?.size?.logd("weather")
                }
                null
            } catch (e: NetworkErrorException) {
                e.message
            } catch (e: HttpException) {
                e.message
            } catch (e: IOException) {
                e.message
            } catch (e: Throwable) {
                e.message
            }

            //deep copy
            val temp = mutableListOf<WeatherModel.WeatherModelItem>()
            weather?.data?.data?.forEach() { temp.add(it.copy()) }
            return if (weather?.data?.status == true){
                Resource.Success(temp)
            }else{
                Resource.Error(handleErrors(weather?.data?.errorMessage))
            }

        } else {
            //deep copy
            val temp = mutableListOf<WeatherModel.WeatherModelItem>()
            weather?.data?.data?.forEach() { temp.add(it.copy()) }
            return if (weather?.data?.status == true){
                Resource.Success(temp)
            }else{
                Resource.Error(handleErrors(weather?.data?.errorMessage))
            }
        }

    }
}