package kr.jiho.kotlinmvvm.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.jiho.kotlinmvvm.R

class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }

    private lateinit var viewModel: SecondViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        // TODO: Use the ViewModel
    }

}