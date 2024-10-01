package com.imagine.jordanpass.tools.network


import com.imagine.jordanpass.tools.model.AttractionsModel
import com.imagine.jordanpass.tools.model.BaseModel
import com.imagine.jordanpass.tools.model.EmergencyNumbersModel
import com.imagine.jordanpass.tools.model.Eticket
import com.imagine.jordanpass.tools.model.FaqModel
import com.imagine.jordanpass.tools.model.InterestsModel
import com.imagine.jordanpass.tools.model.JordanCalendarModel
import com.imagine.jordanpass.tools.model.LocalCustomsModel
import com.imagine.jordanpass.tools.model.LocalExperience
import com.imagine.jordanpass.tools.model.LocalRecommendationModel
import com.imagine.jordanpass.tools.model.MuseumInnerModel
import com.imagine.jordanpass.tools.model.MuseumsModel
import com.imagine.jordanpass.tools.model.OffersModel
import com.imagine.jordanpass.tools.model.PDFGenerationBody
import com.imagine.jordanpass.tools.model.Ticket
import com.imagine.jordanpass.tools.model.Transportation
import com.imagine.jordanpass.tools.model.filter.GettingAroundCategoryModel
import com.imagine.jordanpass.tools.model.landMark.LandMarkModel
import com.imagine.jordanpass.tools.model.story.Stories
import com.imagine.jordanpass.tools.model.story.StoryLocations
import com.imagine.jordanpass.tools.model.story.uploadResponse
import com.imagine.jordanpass.tools.views.weather.WeatherModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface RetrofitApiInterface {

    @POST
    @FormUrlEncoded
    suspend fun getWeather(
        @Url url: String,
        @Field("latitude") lat: Double,
        @Field("longitude") lng: Double
    ): Response<BaseModel<WeatherModel>>

    @GET
    suspend fun getAttractions(@Url url: String): Response<AttractionsModel>

    @GET
    suspend fun getJordanCalendarEvents(@Url url: String): Response<JordanCalendarModel>

    @GET("https://jordangate.imagine.com.jo/api/CalendarJo/GetEvent/{id}")
    suspend fun getJordanCalendarEventDetails(
        @Path("id") id: Int
    ): Response<BaseModel<JordanCalendarModel>>

    @GET
    suspend fun getJordanCalendarEventsWithParams(
        @Url url: String,
        @QueryMap params: Map<String, String>
    ): Response<BaseModel<JordanCalendarModel>>

    @FormUrlEncoded
    @POST
    @Headers("username: jpajzi", "password: ph@95kvm")
    suspend fun getJordanPassTicketDetails(
        @Url url: String,
        @Field("passnu") passNo: String,
        @Field("lastname") lastName: String
    ): Response<BaseModel<ArrayList<Ticket>>>

    @FormUrlEncoded
    @POST
    @Headers("username: jpajzi", "password: ph@95kvm")
    suspend fun getJordanPassTicketDetailsQR(
        @Url url: String,
        @Field("qrcode") qrCode: String
    ): Response<BaseModel<ArrayList<Ticket>>>


    @FormUrlEncoded
    @POST
    @Headers("username: jpajzi", "password: ph@95kvm")
    suspend fun getJordanPassTicketOrder(
        @Url url: String,
        @Field("orderid") orderId: String
    ): Response<BaseModel<ArrayList<Ticket>>>
//==============================================================================================================================================================================

    @FormUrlEncoded
    @POST
    @Headers("username: jpajzi", "password: ph@95kvm")
    suspend fun getJordanETicketDetails(
        @Url url: String,
        @Field("passnu") passNo: String,
        @Field("lastname") lastName: String,
        @Field("MOTA") mota: String
    ): Response<BaseModel<ArrayList<Eticket>>>


    @FormUrlEncoded
    @POST
    @Headers("username: jpajzi", "password: ph@95kvm")
    suspend fun getJordanETicketDetailsQR(
        @Url url: String,
        @Field("qrcode") qrCode: String,
        @Field("MOTA") mota: String
    ): Response<BaseModel<ArrayList<Eticket>>>


    @FormUrlEncoded
    @POST
    @Headers("username: jpajzi", "password: ph@95kvm")
    suspend fun getJordanEticketTicketOrder(
        @Url url: String,
        @Field("orderid") orderId: String
    ): Response<BaseModel<ArrayList<Eticket>>>

    @GET
    suspend fun getStories(@Url url: String): Response<BaseModel<List<Stories>>>


    @GET
    suspend fun getStoryLocations(@Url url: String): Response<BaseModel<List<StoryLocations>>>

    @Multipart
    @POST
//    suspend fun uploadStory(@Url url: String, @Part("Image") image: MultipartBody.Part?, @Field("name") name:String, @Field("description") description:String, @Field("location") location:String, @Field("fcmToken") fcmToken:String): Response<BaseModel<uploadResponse>>}
    suspend fun uploadStory(
        @Url url: String,
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("location") location: RequestBody,
        @Part("fcmToken") fcmToken: RequestBody
    ): Response<BaseModel<uploadResponse>>

    @POST
    @FormUrlEncoded
    suspend fun getPlaces(
        @Url url: String,
        @Field("CenterLat") lat: Double,
        @Field("CenterLon") lng: Double,
        @Field("Radius") radiusInKm: Int
    ): Response<BaseModel<List<LandMarkModel>>>

    @GET
    suspend fun getGettingAroundCategories(@Url url:String): Response<BaseModel<List<GettingAroundCategoryModel>>>

    @GET
    suspend fun getLocalCustoms(@Url url:String): Response<BaseModel<LocalCustomsModel>>

    @GET
    suspend fun getEmergencyNumbers(@Url url:String): Response<BaseModel<List<EmergencyNumbersModel>>>

    @GET
    suspend fun getFaq(@Url url:String): Response<BaseModel<List<FaqModel>>>

    @GET
    suspend fun getLocalRecommendations(@Url url:String): Response<BaseModel<List<LocalRecommendationModel>>>

    @GET
    suspend fun getOffers(@Url url: String) : Response<BaseModel<List<OffersModel.Step>>>

    @GET
    suspend fun getMuseums(@Url url: String) : Response<BaseModel<List<MuseumsModel>>>

    @GET
    suspend fun getMuseumsDetails(@Url url: String, @Query("id") id: Int) : Response<BaseModel<MuseumInnerModel>>

    @GET
    suspend fun getInterests(@Url url: String) : Response<BaseModel<List<InterestsModel>>>

    @POST
    suspend fun generatePDF(@Url url:String,@Body body: PDFGenerationBody) : Response<BaseModel<Any>>

    @GET
    suspend fun getLocalExperience(@Url url: String) : Response<BaseModel<List<LocalExperience>>>

    @GET
    suspend fun getTransportation(@Url url: String) : Response<BaseModel<List<Transportation>>>

    @GET
    suspend fun getPlacesByType(@Url url: String, @Query("Type") type:Int ): Response<BaseModel<List<LandMarkModel>>>

}


