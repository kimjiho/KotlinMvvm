package kr.jiho.kotlinmvvm.repository

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.jiho.kotlinmvvm.net.Photo
import kr.jiho.kotlinmvvm.net.RandomApi

class ApiRepository {

    private val api = RandomApi.create()

    fun getPhotoList(idx: Int): Observable<ArrayList<Photo>> = api
        .getPhotoListByPage(idx)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}