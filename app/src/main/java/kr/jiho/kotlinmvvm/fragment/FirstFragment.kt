package kr.jiho.kotlinmvvm.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.first_fragment.*
import kr.jiho.kotlinmvvm.databinding.FirstFragmentBinding

class FirstFragment : Fragment() {

    private lateinit var viewModel: FirstViewModel
    private lateinit var fBinding: FirstFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.w("DEBUG", "onCreateView")
        fBinding = FirstFragmentBinding.inflate(inflater, container, false)
        //recyclerView.layoutManager = LinearLayoutManager(context)
        //adapter = RecyclerAdapter(list)
        //recyclerView.adapter = adapter
        return fBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.w("DEBUG", "onActivityCreated")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w("DEBUG", "onViewCreated")
        //viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)
        viewModel = FirstViewModel(fBinding)
        viewModel.init(view.context)
        viewModel.getPhotoList()

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                if(itemTotalCount == lastVisibleItemPosition) {
                    Log.w("DEBUG", "lastPosition!")

                    viewModel.run {
                        addIndex()

                        if(idxPosition.value == 1)
                            return

                        getPhotoList()
                    }
                }
            }
        })
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.w("DEBUG", "onViewStateRestored")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("DEBUG", "onDestroy")

        viewModel.run {
            clearDispose()
        }
    }
}