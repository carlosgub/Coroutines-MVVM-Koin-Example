package com.carlosgub.coroutines.features.books.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carlosgub.coroutines.R
import com.carlosgub.coroutines.features.books.presentation.model.PostVM
import kotlinx.android.synthetic.main.post_item.view.*

class RVPostAdapter : RecyclerView.Adapter<RVPostAdapter.ViewHolder>() {

    private val list: MutableList<PostVM> = mutableListOf()
    private var listener: Listener? = null

    fun add(postVM: PostVM) {
        list.add(postVM)
        notifyItemInserted(this.itemCount)
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onPostClicked(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(postVM: PostVM) {
            itemView.tvPostItemTitle?.text = postVM.title
            itemView.cvPostItem?.setOnClickListener {
                listener?.onPostClicked(postVM.id ?: 1)
            }
        }
    }
}

@BindingAdapter("data")
fun setRecyclerViewProperties(recyclerView: RecyclerView?, data: PostVM?) {
    val adapter = recyclerView?.adapter
    if (adapter is RVPostAdapter && data != null) {
        adapter.add(data)
    }
}