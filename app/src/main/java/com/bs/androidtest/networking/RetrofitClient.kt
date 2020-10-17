package com.bs.androidtest.networking

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }

    private val headerAuthorizationInterceptor =
        Interceptor { chain: Interceptor.Chain ->
            var request = chain.request()
            val headers = request.headers().newBuilder()
                .add("Connection", "close")
                .add("Cache-Control", "no-cache")
                .add("Cache-Control", "no-store").build()
            request = request.newBuilder().headers(headers).build()
            chain.proceed(request)
        }


    private val myHttpClient: OkHttpClient by lazy {
        OkHttpClient().newBuilder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(false)
            .addInterceptor(headerAuthorizationInterceptor)
            .build()
    }


    private val gson: Gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(myHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }


}