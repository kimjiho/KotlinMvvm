package kr.jiho.kotlinmvvm.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.jiho.kotlinmvvm.adapter.DummyRecyclerAdapter
import kr.jiho.kotlinmvvm.databinding.SecondFragmentBinding
import kr.jiho.kotlinmvvm.service.DownloadService
import kr.jiho.kotlinmvvm.ui.LoginActivity

class SecondFragment : Fragment() {

    private lateinit var viewModel: SecondViewModel
    private lateinit var binding: SecondFragmentBinding

    private val arrString = arrayOf("abcde\n12345", "fghij\n12345", "klmnop\n12345", "qrstu\n12345", "vwxyz\n12345", "abcde\n12345", "fghij\n12345", "klmnop\n12345", "qrstu\n12345", "vwxyz\n12345")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = SecondFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = SecondViewModel(binding)
        viewModel.init(view.context)

        // dummy
        val adapter = DummyRecyclerAdapter(arrString)
        binding.recyclerTwo.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.recyclerTwo.adapter = adapter

        binding.btnLogout.setOnClickListener {
            Intent(view.context, LoginActivity::class.java).apply {
                startActivity(this)
                activity?.finishAffinity()
            }
        }

        binding.btnStart.setOnClickListener {
            Intent(view.context, DownloadService::class.java).apply {
                context?.startForegroundService(this)
            }
        }

        binding.btnEnd.setOnClickListener {
            Intent(view.context, DownloadService::class.java).apply {
                context?.stopService(this)
            }
        }

        viewModel.getPhotoList()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearDispose()
    }

}