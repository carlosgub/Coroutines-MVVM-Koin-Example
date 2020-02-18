package com.carlosgub.coroutines.features.books.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carlosgub.coroutines.R
import com.carlosgub.coroutines.features.books.presentation.model.PostVM
import kotlinx.android.synthetic.main.post_item.view.*

class RVPostAdapter : ListAdapter<PostVM,RVPostAdapter.ViewHolder>(PostDiff) {

    private var listener: Listener? = null

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(postVM: PostVM) {
            itemView.tvPostItemTitle?.text = postVM.title
            itemView.cvPostItem?.setOnClickListener {
                listener?.onPostClicked(postVM.id ?: 1)
            }
        }
    }

    object PostDiff : DiffUtil.ItemCallback<PostVM>() {
        override fun areItemsTheSame(oldItem: PostVM, newItem: PostVM) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PostVM, newItem: PostVM) =
            oldItem == newItem
    }
}

@BindingAdapter("data")
fun setRecyclerViewProperties(recyclerView: RecyclerView?, data: List<PostVM>?) {
    val adapter = recyclerView?.adapter
    if (adapter is RVPostAdapter && data != null) {
        adapter.submitList(data)
    }
}