package com.ssafy.hajo.network

import com.google.gson.GsonBuilder
import com.ssafy.hajo.network.api.*

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://i7d107.p.ssafy.io/api/"
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat("yyyy-MM-dd").create()))
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    .build()


object RegistrationService {
    val retrofitService by lazy {
        retrofit.create(UserApi::class.java)
    }
}

object PlanDetailService {
    val retrofitService by lazy {
        retrofit.create(PlanDetailApi::class.java)
    }
}

object DiaryService {
    val retrofitService by lazy {
        retrofit.create(DiaryApi::class.java)
    }
}


object CompetitionService {
    val retrofitService by lazy {
        retrofit.create(CompetitionApi::class.java)
    }
}

object BoardService{
    val retrofitService by lazy {
        retrofit.create(BoardApi::class.java)
    }
}

object HomeService{
    val retrofitService by lazy {
        retrofit.create(HomeApi::class.java)

    }
}

object SettingService{
    val retrofitService by lazy {
        retrofit.create(QuestApi::class.java)
    }
}
