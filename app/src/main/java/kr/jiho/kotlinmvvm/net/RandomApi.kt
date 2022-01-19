package kr.jiho.kotlinmvvm.net

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface RandomApi {

    @GET("v2/list")
    fun getPhotoList(): Observable<Photo>
}