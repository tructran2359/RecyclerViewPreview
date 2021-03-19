package com.me.recyclerviewpreview.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://demo1511318.mockable.io/"

class ApiManager {
    private var service: ApiService? = null

    fun getService(): ApiService? {
        if (service == null) {
            val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            service = retrofit.create(ApiService::class.java)
        }
        return service
    }
}