package kr.jiho.kotlinmvvm.fragment

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import kr.jiho.kotlinmvvm.adapter.RecyclerAdapter
import kr.jiho.kotlinmvvm.databinding.FirstFragmentBinding
import kr.jiho.kotlinmvvm.net.Photo
import kr.jiho.kotlinmvvm.repository.ApiRepository

class FirstViewModel(viewBinding: FirstFragmentBinding) : ViewModel() {

    // composite
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    // viewBinding
    private var binding: FirstFragmentBinding = viewBinding

    // repository
    private val repo: ApiRepository = ApiRepository()

    // recycler Adapter
    private val adapter = RecyclerAdapter(this)

    // adapter list
    val photoList = ArrayList<Photo>()

    // api page index
    private val _index = MutableLiveData<Int>()

    val idxPosition: LiveData<Int>
        get() = _index

    fun init(context: Context) {
        _index.value = 1
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    fun addIndex() {
        _index.value = (_index.value)?.plus(1)
    }

    fun reduceIndex() {
        if(_index.value!! > 1) {
            _index.value = (_index.value)?.minus(1)
        }
    }

    fun getPhotoList() {
        // loading visible
        binding.loadingProgress.visibility = View.VISIBLE

        /*
        * 1. Repository 에서 리트로핏 과 Api 클래스를 만들고
        *    기능을 호출한다.
        * Repository > Retrofit, ApiClass, get function
        *
        * */

        val item = repo.getPhotoList(_index.value!!).subscribeWith(object: DisposableObserver<ArrayList<Photo>>(){
            override fun onNext(t: ArrayList<Photo>?) {
                if(t != null) {
                    if(t.size == 0) {
                        reduceIndex()
                    }

                    photoList.addAll(t)
                }
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()

                // loading hidden
                binding.loadingProgress.visibility = View.GONE
            }

            override fun onComplete() {
                adapter.notifyDataSetChanged()

                // loading hidden
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

        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}