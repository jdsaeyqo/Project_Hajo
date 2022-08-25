package com.ssafy.hajo.network.api

import com.google.gson.GsonBuilder
import com.ssafy.hajo.repository.dto.request.LoginGoogleRequestModel
import com.ssafy.hajo.repository.dto.response.LoginGoogleResponseModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleLoginApi {

    @POST("oauth2/v4/token")
    suspend fun getAccessToken(@Body request : LoginGoogleRequestModel) : Response<LoginGoogleResponseModel>



    companion object{
        private val gson = GsonBuilder().setLenient().create()

        fun loginRetrofit(baseUrl: String): GoogleLoginApi {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(GoogleLoginApi::class.java)
        }
    }
}