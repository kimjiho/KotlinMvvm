package kr.jiho.kotlinmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.jiho.kotlinmvvm.R

class RecyclerAdapter(private val dataSet: Array<String>) : RecyclerView.Adapter<RecyclerAdapter.RecycleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        // todo view bind

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycle_item, parent, false)
        return RecycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        // todo setting value

        holder.tv.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class RecycleViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv_title)
    }


}

