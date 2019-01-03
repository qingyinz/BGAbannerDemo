package com.example.mingyue.bgabannerdemo.application

import android.app.Application
import com.example.mingyue.bgabannerdemo.engine.Engine
import com.example.mingyue.bgabannerdemo.network.ApiClient
import com.facebook.drawee.backends.pipeline.Fresco
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by leimo on 2019/1/2.
 */
 class App : Application(){
    override fun onCreate() {
        super.onCreate()
        ApiClient.instance.init()
    }

 }

