package kr.jiho.kotlinmvvm.fragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.first_fragment.*
import kr.jiho.kotlinmvvm.R
import kr.jiho.kotlinmvvm.adapter.RecyclerAdapter
import kr.jiho.kotlinmvvm.databinding.FirstFragmentBinding
import kr.jiho.kotlinmvvm.net.Photo
import kr.jiho.kotlinmvvm.net.RandomApi
import kr.jiho.kotlinmvvm.net.RetrofitBuilder

class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    private lateinit var viewModel: FirstViewModel

    lateinit var fBinding: FirstFragmentBinding

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.w("DEBUG", "onCreateView")
        fBinding = FirstFragmentBinding.inflate(inflater, container, false)
        return fBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.w("DEBUG", "onActivityCreated")
        viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w("DEBUG", "onViewCreated")
        compositeDisposable.addAll(photoObserver)
        getPhotoList()
    }

    // todo move to viewModel
    private fun getPhotoList() {
        val retrofit = RetrofitBuilder().getInstance()
        val api = retrofit.create(RandomApi::class.java)

        api.getPhotoListByPage(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(photoObserver)
    }

    val list = ArrayList<Photo>()
    private val photoObserver = object: DisposableObserver<ArrayList<Photo>>() {
        override fun onNext(t: ArrayList<Photo>?) {
            if(t != null) list.addAll(t)
        }

        override fun onError(e: Throwable?) {
            fBinding.loadingProgress.visibility = View.GONE
            e?.printStackTrace()
        }

        override fun onComplete() {
            fBinding.loadingProgress.visibility = View.GONE

            val adapter = RecyclerAdapter(list)

            fBinding.recyclerView.layoutManager = LinearLayoutManager(context)
            fBinding.recyclerView.adapter = adapter
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.w("DEBUG", "onViewStateRestored")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.w("DEBUG", "onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.w("DEBUG", "onDetach")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.w("DEBUG", "onDestroyView")

//        if(!photoObserver.isDisposed) {
//            photoObserver.dispose()
//        }

        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("DEBUG", "onDestroy")

        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}