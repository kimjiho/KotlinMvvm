package kr.jiho.kotlinmvvm.fragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.first_fragment.*
import kr.jiho.kotlinmvvm.R
import kr.jiho.kotlinmvvm.adapter.RecyclerAdapter
import kr.jiho.kotlinmvvm.databinding.FirstFragmentBinding

class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    private lateinit var viewModel: FirstViewModel

    lateinit var fBinding: FirstFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        val arrString = arrayOf("하나", "두울", "셋", "넷", "다섯", "여섯", "일곱", "여덜", "아홉", "열!")
        val adapter = RecyclerAdapter(arrString)

        fBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        fBinding.recyclerView.adapter = adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

}