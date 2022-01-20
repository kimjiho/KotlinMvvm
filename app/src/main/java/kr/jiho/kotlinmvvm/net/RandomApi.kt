package kr.jiho.kotlinmvvm.net

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomApi {

    @GET("v2/list")
    fun getPhotoList(): Observable<ArrayList<Photo>>

    //val photoList = "https://picsum.photos/v2/list?page=1&limit=20"
    @GET("v2/list?limit=20")
    fun getPhotoListByPage(@Query("page")pageNum:Int): Observable<ArrayList<Photo>>

    companion object {

        fun create(): RandomApi {
            val retrofit = RetrofitBuilder().getInstance()
            return retrofit.create(RandomApi::class.java)
        }
    }
}