package com.example.mingyue.bgabannerdemo.network

import com.example.mingyue.bgabannerdemo.BuildConfig
import com.example.mingyue.bgabannerdemo.engine.Engine
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by leimo on 2019/1/2.
 */
class ApiClient private constructor() {
    lateinit var service: Engine

    private object Holder {
        val INSTANCE = ApiClient()
    }
    companion object {
        val instance by lazy { Holder.INSTANCE }
    }
    fun init() {//在Application的onCreate中调用一次即可
        val okHttpClient = OkHttpClient().newBuilder()
                //输入http连接时的log，也可添加更多的Interceptor
                .addInterceptor(HttpLoggingInterceptor().setLevel(
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                )).build()
        //链式调用 retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl("http://bgashare.bingoogolapple.cn/banner/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        service = retrofit.create(Engine::class.java)
    }
}