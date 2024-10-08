package kr.jiho.kotlinmvvm.fragment

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.observers.DisposableObserver
import kr.jiho.kotlinmvvm.adapter.RecyclerAdapter
import kr.jiho.kotlinmvvm.databinding.FirstFragmentBinding
import kr.jiho.kotlinmvvm.model.CommonViewModel
import kr.jiho.kotlinmvvm.net.Photo

class FirstViewModel(viewBinding: FirstFragmentBinding) : CommonViewModel() {

    /***
     * CompositeDisposable
     */
    //private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Api repository
     */
    //private val repo: ApiRepository = ApiRepository(CommonUrl.photoList)

    /**
     * View Binding
     */
    private var binding: FirstFragmentBinding = viewBinding

    // recycler Adapter
    //private val adapter = RecyclerAdapter(this)

    // adapter list
    //val photoList = ArrayList<Photo>()

    override var adapter: RecyclerAdapter = RecyclerAdapter(this, 1)

    /**
     * Mutable LiveData
     * Page Index
     */
    private val _index = MutableLiveData<Int>()

    val idxPosition: LiveData<Int>
        get() = _index

    /**
     * 초기화 함수
     */
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

    /**
     * API PhotoList
     * 포토 리스트를 가져온다.
     */
    fun getPhotoList() {
        binding.loadingProgress.visibility = View.VISIBLE

        val item = repo.getPhotoList(_index.value!!).subscribeWith(object: DisposableObserver<ArrayList<Photo>>(){
               override fun onNext(t: ArrayList<Photo>) {
                   if(t.size == 0) {
                       reduceIndex()
                   }

                   photoList.addAll(t)
               }

            override fun onError(e: Throwable) {
                e.printStackTrace()
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

    /**
     * Disposable 해제
     */
    override fun onCleared() {
        super.onCleared()

        photoList.clear()

        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}