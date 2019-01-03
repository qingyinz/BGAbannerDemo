package com.example.mingyue.bgabannerdemo.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import cn.bingoogolapple.bgabanner.BGABanner
import cn.bingoogolapple.bgabanner.BGALocalImageSize
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mingyue.bgabannerdemo.R
import com.example.mingyue.bgabannerdemo.model.BannerModel
import com.example.mingyue.bgabannerdemo.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() ,BGABanner.Adapter<ImageView, String>, BGABanner.Delegate<ImageView, String>{
    //配置numBanner数据源
    //  通过传入数据模型并结合 Adapter 的方式配置数据源。这种方式主要用于加载网络图片
    override fun fillBannerItem(banner: BGABanner?, itemView: ImageView, model: String?, position: Int) {
        Glide.with(this)
                .load(model)
                .apply(RequestOptions().placeholder(R.drawable.uoko_guide_background_3).error(R.drawable.uoko_guide_background_3).dontAnimate().centerCrop())
                .into(itemView)
    }

    override fun onBannerItemClick(banner: BGABanner?, itemView: ImageView?, model: String?, position: Int) {
        Toast.makeText(this, "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show()
    }

    var numBanner: BGABanner? = null
    var mbannerModel: BannerModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //BGABanner绑定id
        var poitBanner = findViewById<BGABanner>(R.id.poitBanner)
        numBanner=findViewById<BGABanner>(R.id.numBanner)

        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        var localImageSize: BGALocalImageSize = BGALocalImageSize(720, 1280, 320F, 640F)

        //配置poitBanner数据源
        //通过传入图片资源 id 的方式配置数据源，主要用于引导页每一页都是只显示图片的情况
        poitBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP, R.drawable.uoko_guide_background_1,
                R.drawable.uoko_guide_background_2, R.drawable.uoko_guide_background_3)
        //轮播图点击事件
        poitBanner?.setDelegate { banner, itemView, model, position ->
            Toast.makeText(banner.context, "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show()
        }
        //配置适配器和点击监听事件
        numBanner?.setAdapter(this)
        numBanner?.setDelegate(this)
        //网络请求
        ApiClient.instance.service?.fetchItemsWithItemCount(5)?.enqueue(object : Callback<BannerModel> {
            override fun onFailure(call: Call<BannerModel>?, t: Throwable?) {

            }
            override fun onResponse(call: Call<BannerModel>?, response: Response<BannerModel>?) {
                  mbannerModel= response?.body()
                //配置numBanner数据源
                // 通过传入数据模型并结合 Adapter 的方式配置数据源。这种方式主要用于加载网络图片
                numBanner?.setData(mbannerModel?.imgs, mbannerModel?.tips)
            }
        })
    }
}
