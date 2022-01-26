package kr.jiho.kotlinmvvm.model

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kr.jiho.kotlinmvvm.adapter.RecyclerAdapter
import kr.jiho.kotlinmvvm.const.CommonUrl
import kr.jiho.kotlinmvvm.net.Photo
import kr.jiho.kotlinmvvm.repository.ApiRepository

abstract class CommonViewModel : ViewModel(){

    /***
     * CompositeDisposable
     */
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Api repository
     */
    val repo: ApiRepository = ApiRepository(CommonUrl.photoList)

    // recycler Adapter
    abstract var adapter:RecyclerAdapter

    // adapter list
    val photoList = ArrayList<Photo>()
}