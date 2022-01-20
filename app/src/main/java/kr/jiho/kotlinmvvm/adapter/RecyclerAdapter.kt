package kr.jiho.kotlinmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.jiho.kotlinmvvm.R
import kr.jiho.kotlinmvvm.fragment.FirstViewModel

class RecyclerAdapter(private val viewModel: FirstViewModel) : RecyclerView.Adapter<RecyclerAdapter.RecycleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycle_item, parent, false)
        return RecycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val value = viewModel.photoList[position]
        val author = value.author
        //val strId = value.id
        val downloadUrl = value.download_url

        holder.tv.text = author

        // load image
        Glide.with(holder.itemView)
            .load(downloadUrl)
            .into(holder.imgView)
    }

    override fun getItemCount(): Int {
        return viewModel.photoList.size
    }

    class RecycleViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv_title)
        val imgView: ImageView = view.findViewById(R.id.imgView)
    }

}

