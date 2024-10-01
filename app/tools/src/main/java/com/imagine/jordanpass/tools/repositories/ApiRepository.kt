package com.imagine.jordanpass.tools.repositories


import com.imagine.jordanpass.tools.model.PDFGenerationBody
import com.imagine.jordanpass.tools.network.RetrofitApiInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiInterface: RetrofitApiInterface)  {

    suspend fun getWeather(url:String,lat:Double,lng:Double) = apiInterface.getWeather(url,lat,lng)

    suspend fun getAttractions(url: String) = apiInterface.getAttractions(url)

    suspend fun getJordanCalendarEvents(url: String) = apiInterface.getJordanCalendarEvents(url)
    suspend fun getJordanCalendarEventsWithParams(url: String,params: HashMap<String,String>) = apiInterface.getJordanCalendarEventsWithParams(url,params)

    suspend fun getJordanCalendarEventDetails(id:Int) = apiInterface.getJordanCalendarEventDetails(id)

    suspend fun getJordanPassTicketDetails(url: String,passNo:String,lastName:String) = apiInterface.getJordanPassTicketDetails(url,passNo,lastName)

    suspend fun getJordanPassTicketDetailsQR(url: String,qrCode:String) = apiInterface.getJordanPassTicketDetailsQR(url,qrCode)


    suspend fun getJordanPassTicketOrder(url: String,orderid:String) = apiInterface.getJordanPassTicketOrder(url,orderid)

    suspend fun getJordanEticketTicketOrder(url: String,orderid:String) = apiInterface.getJordanEticketTicketOrder(url,orderid)

    suspend fun getJordanETicketDetails(url: String,passNo:String,lastName:String,mota:String) = apiInterface.getJordanETicketDetails(url,passNo,lastName,mota)

    suspend fun getJordanETicketDetailsQR(url: String,qrCode:String,mota:String) = apiInterface.getJordanETicketDetailsQR(url,qrCode,mota)

    suspend fun uploadStory(url: String, image: MultipartBody.Part?, name:RequestBody, description:RequestBody, location:RequestBody, fcmToken:RequestBody) = apiInterface.uploadStory(url,image,name,description,location,fcmToken)

    suspend fun getStories(url: String) = apiInterface.getStories(url)

    suspend fun getStoryLocations(url: String) = apiInterface.getStoryLocations(url)

    suspend fun getPlaces(url:String,lat:Double,lng:Double,radiusInKm:Int) = apiInterface.getPlaces(url,lat,lng, radiusInKm)

    suspend fun getCategories(url:String) = apiInterface.getGettingAroundCategories(url)

    suspend fun getLocalCustoms(url:String) = apiInterface.getLocalCustoms(url)

    suspend fun getEmergencyNumbers(url:String) = apiInterface.getEmergencyNumbers(url)

    suspend fun getFaq(url:String) = apiInterface.getFaq(url)

    suspend fun getLocalRecommendations(url:String) = apiInterface.getLocalRecommendations(url)

    suspend fun getOffers(url: String) = apiInterface.getOffers(url)

    suspend fun getMuseums(url: String) = apiInterface.getMuseums(url)

    suspend fun getMuseumsDetails(url: String,id: Int) = apiInterface.getMuseumsDetails(url,id)

    suspend fun getInterests(url: String) = apiInterface.getInterests(url)

    suspend fun generatePDF(url:String,body:PDFGenerationBody) = apiInterface.generatePDF(url,body)

    suspend fun getLocalExperience(url: String) = apiInterface.getLocalExperience(url)

    suspend fun getTransportation(url: String) = apiInterface.getTransportation(url)

    suspend fun getPlacesByType(url: String,type:Int) = apiInterface.getPlacesByType(url,type)


}