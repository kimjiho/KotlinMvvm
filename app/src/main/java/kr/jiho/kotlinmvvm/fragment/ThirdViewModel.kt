package kr.jiho.kotlinmvvm.fragment

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import kr.jiho.kotlinmvvm.adapter.RecyclerUserAdapter
import kr.jiho.kotlinmvvm.const.CommonUrl
import kr.jiho.kotlinmvvm.databinding.ThirdFragmentBinding
import kr.jiho.kotlinmvvm.net.User
import kr.jiho.kotlinmvvm.net.UserList
import kr.jiho.kotlinmvvm.repository.ApiRepository

class ThirdViewModel(viewBiding: ThirdFragmentBinding) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val api = ApiRepository(CommonUrl.userList)

    val binding = viewBiding

    val userList: ArrayList<User> = ArrayList()
    val adapter = RecyclerUserAdapter(this)

    fun init(context: Context) {
        binding.recyclerViewThird.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewThird.adapter = adapter
    }

    fun getUserList() {
        val item = api.getUserList(1)
            .subscribeWith(object: DisposableObserver<UserList>(){
                override fun onNext(t: UserList?) {
                    for(user: User in t?.data!!) {
                        Log.w("DEBUG", "user: ${user.email}")
                    }

                    userList.addAll(t.data)
                }

                override fun onError(e: Throwable?) {
                    e?.printStackTrace()
                    binding.loadingProgress.visibility = View.GONE
                }

                override fun onComplete() {
                    Log.w("DEBUG", "complete")
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

        if(!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}