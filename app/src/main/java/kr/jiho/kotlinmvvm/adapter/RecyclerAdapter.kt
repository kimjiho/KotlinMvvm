package kr.jiho.kotlinmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.jiho.kotlinmvvm.R
import kr.jiho.kotlinmvvm.net.Photo

class RecyclerAdapter(private val dataSet: ArrayList<Photo>) : RecyclerView.Adapter<RecyclerAdapter.RecycleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycle_item, parent, false)
        return RecycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val value = dataSet[position]
        val author = value.author

        holder.tv.text = author
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class RecycleViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv_title)
    }


}

