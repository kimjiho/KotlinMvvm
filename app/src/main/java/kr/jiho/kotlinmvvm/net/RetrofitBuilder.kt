package kr.jiho.kotlinmvvm.net

import kr.jiho.kotlinmvvm.const.CommonUrl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    private var retrofit: Retrofit? = null

    fun getInstance(): Retrofit {
        if(retrofit == null)
        {
            retrofit = Retrofit.Builder()
                .baseUrl(CommonUrl.photoList)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!
    }
}