package kr.jiho.kotlinmvvm.fragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fBinding = FirstFragmentBinding.inflate(inflater, container, false)

        return fBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)

        // todo: ViewModel call git api or animal api
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPhotoList()
    }

    private fun getPhotoList() {
        val retrofit = RetrofitBuilder().getInstance()
        val api = retrofit.create(RandomApi::class.java)

        api.getPhotoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(photoObserver)
    }

    val list = ArrayList<Photo>()
    private val photoObserver = object: DisposableObserver<Photo>() {
        override fun onNext(t: Photo?) {
            if(t != null) list.add(t)
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
}