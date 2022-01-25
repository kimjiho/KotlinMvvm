package kr.jiho.kotlinmvvm.net

import io.reactivex.rxjava3.core.Observable
import kr.jiho.kotlinmvvm.const.CommonUrl
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomApi {

    @GET("v2/list")
    fun getPhotoList(): Observable<ArrayList<Photo>>

    //"https://picsum.photos/v2/list?page=1&limit=20"
    @GET("v2/list?limit=20")
    fun getPhotoListByPage(@Query("page")pageNum:Int): Observable<ArrayList<Photo>>

    @GET("public/v1/users")
    fun getUserList(@Query("page")pageNum:Int): Observable<UserList>

    companion object {
        fun create(url: String): RandomApi {
            val retrofit = RetrofitBuilder().getInstance(url)
            return retrofit.create(RandomApi::class.java)
        }
    }
}