package kr.jiho.kotlinmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.jiho.kotlinmvvm.R

class DummyRecyclerAdapter(private val dataSet: Array<String>) : RecyclerView.Adapter<DummyRecyclerAdapter.RecycleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycle_main_item_1, parent, false)
        return RecycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val value = dataSet[position]

        holder.tv.text = value
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class RecycleViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv_pic_title)
        //val imgView: ImageView = view.findViewById(R.id.imgView)
    }

}

