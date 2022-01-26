package kr.jiho.kotlinmvvm.fragment

import android.content.Context
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import kr.jiho.kotlinmvvm.adapter.RecyclerAdapter
import kr.jiho.kotlinmvvm.const.CommonUrl
import kr.jiho.kotlinmvvm.databinding.SecondFragmentBinding
import kr.jiho.kotlinmvvm.model.CommonViewModel
import kr.jiho.kotlinmvvm.net.Photo
import kr.jiho.kotlinmvvm.repository.ApiRepository

class SecondViewModel(private val binding: SecondFragmentBinding) : CommonViewModel() {

    /***
     * CompositeDisposable
     */
    //private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Api repository
     */
    //private val repo: ApiRepository = ApiRepository(CommonUrl.photoList)

    override var adapter: RecyclerAdapter = RecyclerAdapter(this)

    fun init(context: Context) {
        val drawable = AppCompatResources.getDrawable(context, android.R.drawable.gallery_thumb)

        // load image
        Glide.with(context)
            .load("https://placedog.net/200/200?random")
            .into(binding.imgTop)
            .onLoadFailed(drawable)

        binding.recyclerOne.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.recyclerOne.adapter = adapter
    }

    fun getPhotoList() {
        binding.loadingProgress.visibility = View.VISIBLE

        val item = repo.getDefaultPhotoList()
            .subscribeWith(object: DisposableObserver<ArrayList<Photo>>(){
            override fun onNext(t: ArrayList<Photo>?) {
                if(t != null) {
                    photoList.addAll(t)
                }
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                binding.loadingProgress.visibility = View.GONE
            }

            override fun onComplete() {
                adapter.notifyDataSetChanged()
                binding.loadingProgress.visibility = View.GONE
            }
        })

        compositeDisposable.add(item)
    }

    fun clearDispose() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()

        photoList.clear()

        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}