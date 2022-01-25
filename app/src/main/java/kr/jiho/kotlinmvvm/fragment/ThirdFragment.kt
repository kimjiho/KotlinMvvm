package kr.jiho.kotlinmvvm.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.jiho.kotlinmvvm.R
import kr.jiho.kotlinmvvm.databinding.ThirdFragmentBinding

class ThirdFragment : Fragment() {

    private lateinit var viewModel: ThirdViewModel
    private lateinit var _binding: ThirdFragmentBinding

    private val binding: ThirdFragmentBinding
        get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = ThirdFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ThirdViewModel(binding)
        viewModel.init(view.context)
        viewModel.getUserList()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearDispose()
    }

}