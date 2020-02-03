package com.carlosgub.coroutines.features.books.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlosgub.coroutines.R
import com.carlosgub.coroutines.features.books.presentation.adapters.RVPostAdapter
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostViewModel
import com.carlosgub.coroutines.features.books.presentation.viewmodel.viewstate.PostVS
import kotlinx.android.synthetic.main.post_activity.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostActivity : AppCompatActivity(), RVPostAdapter.Listener {

    private val viewModel: PostViewModel by viewModel()
    private val mAdapter = RVPostAdapter()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_activity)
        viewModelComplete()

        mAdapter.setListener(this)

        rvPost.apply {
            layoutManager = LinearLayoutManager(this@PostActivity)
            adapter = mAdapter
        }

        lifecycleScope.launch {
            pbPost.visibility = View.VISIBLE
            viewModel.getPosts().collect {
                mAdapter.add(it)
            }
            pbPost.visibility = View.GONE
        }
    }

    private fun viewModelComplete() {
        viewModel.state.observe(this, Observer {
            when (it) {
                is PostVS.Loading -> {

                }
                is PostVS.AddPost -> {
                    mAdapter.add(it.post)
                }
            }
        })
    }

    override fun onPostClicked(id: Int) {
        startActivity(PostDetailActivity.getInstance(this, id))
    }
}
