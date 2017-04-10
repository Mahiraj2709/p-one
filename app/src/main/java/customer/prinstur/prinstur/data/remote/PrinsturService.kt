package customer.prinstur.prinstur.data.remote

import android.content.Context
import com.google.gson.GsonBuilder
import customer.prinstur.prinstur.data.model.ApiResponse
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

/**
 * Created by admin on 11/21/2016.
 */

interface PrinsturService {

    @POST("signup")
    @Multipart
    fun signUp(@PartMap requestMap: Map<String, RequestBody>): Call<ApiResponse>

    @POST("login")
    @FormUrlEncoded
    fun login(@FieldMap params: Map<String, String>): Call<ApiResponse>

    @POST("forgotpassword")
    @FormUrlEncoded
    fun forgotPassword(@FieldMap params: Map<String, String>): Call<ApiResponse>

    @POST("logout")
    @FormUrlEncoded
    fun logout(@FieldMap params: Map<String, String>): Call<ApiResponse>

    @POST("staticpages")
    @FormUrlEncoded
    fun getStaticPages(@FieldMap params: Map<String, String>): Call<ApiResponse>

    @POST("getprofile")
    @FormUrlEncoded
    fun getProfile(@FieldMap params: Map<String, String>): Call<ApiResponse>

    @POST("editprofile")
    @Multipart
    fun editProfile(@PartMap params: Map<String, RequestBody>): Call<ApiResponse>

    @POST("changepassword")
    @FormUrlEncoded
    fun resetPassword(@FieldMap params: Map<String, String>): Call<ApiResponse>

    @POST("getservicetype")
    @FormUrlEncoded
    fun resetGetAllServices(@FieldMap params: Map<String, String>): Call<ApiResponse>

    @POST("getallonlinemechanic")
    @FormUrlEncoded
    fun getMechForService(@FieldMap params: Map<String, String>): Call<ApiResponse>

    @POST("getonlinemechanicbyservicetype")
    @FormUrlEncoded
    fun getMechByServiceType(@FieldMap params: Map<String, String>): Call<ApiResponse>

    @POST("sendrequest")
    @FormUrlEncoded
    fun getSendRequest(@FieldMap params: Map<String, String>): Call<ApiResponse>

    @POST("getproviderdetails")
    @FormUrlEncoded
    fun getMechanicDetail(@FieldMap requestMap: Map<String, String>): Call<ApiResponse>

    @POST("acceptoffer")
    @FormUrlEncoded
    fun acceptOffer(@FieldMap requestMap: Map<String, String>): Call<ApiResponse>

    @POST("cancelrequest")
    @FormUrlEncoded
    fun cancelRequest(@FieldMap requestMap: Map<String, String>): Call<ApiResponse>

    @POST("addreview")
    @FormUrlEncoded
    fun rateMechanic(@FieldMap requestMap: Map<String, String>): Call<ApiResponse>

    @POST("updatelatlong")
    @FormUrlEncoded
    fun updateLatLng(@FieldMap requestMap: Map<String, String>): Call<ApiResponse>

    /********
     * Factory class that sets up a new ribot services
     */
    object Factory {

        fun makeFairRepairService(context: Context): PrinsturService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build()

            val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(PrinsturService.ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            return retrofit.create(PrinsturService::class.java)
        }

    }

    companion object {
        val ENDPOINT = "http://prinstur.onsisdev.info/customerapi/"
    }
}
