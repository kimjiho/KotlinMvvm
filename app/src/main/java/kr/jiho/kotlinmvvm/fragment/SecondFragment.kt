package kr.jiho.kotlinmvvm.fragment

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
import kotlinx.android.synthetic.main.second_fragment.*
import kr.jiho.kotlinmvvm.adapter.DummyRecyclerAdapter
import kr.jiho.kotlinmvvm.databinding.SecondFragmentBinding

class SecondFragment : Fragment() {

    private lateinit var viewModel: SecondViewModel

    private val arrString = arrayOf("abcde\n12345", "fghij\n12345", "klmnop\n12345", "qrstu\n12345", "vwxyz\n12345", "abcde\n12345", "fghij\n12345", "klmnop\n12345", "qrstu\n12345", "vwxyz\n12345")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = SecondFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)

        val drawable = AppCompatResources.getDrawable(view.context, android.R.drawable.gallery_thumb)

        // load image
        Glide.with(view.context)
            .load("https://placedog.net/200/200?random")
            .into(img_top)
            .onLoadFailed(drawable)

        val adapter = DummyRecyclerAdapter(arrString)
        recycler_one.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recycler_one.adapter = adapter

        recycler_two.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recycler_two.adapter = adapter

        loadingProgress.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}