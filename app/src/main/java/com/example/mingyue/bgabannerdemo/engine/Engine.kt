package com.example.mingyue.bgabannerdemo.engine


import com.example.mingyue.bgabannerdemo.model.BannerModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface Engine {

    @GET("{itemCount}item.json")
    fun fetchItemsWithItemCount(@Path("itemCount") itemCount: Int): Call<BannerModel>
}
