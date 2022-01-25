package kr.jiho.kotlinmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.jiho.kotlinmvvm.R
import kr.jiho.kotlinmvvm.fragment.ThirdViewModel

class RecyclerUserAdapter(private val viewModel: ThirdViewModel) : RecyclerView.Adapter<RecyclerUserAdapter.RecycleViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecycleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycle_user_item, parent, false)
        return RecycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val user = viewModel.userList.get(position)

        holder.tvName.text = user.name
        holder.tvEmail.text = user.email
    }

    override fun getItemCount(): Int {
        return viewModel.userList.size
    }

    class RecycleViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvEmail: TextView = view.findViewById(R.id.tv_email)
    }
}