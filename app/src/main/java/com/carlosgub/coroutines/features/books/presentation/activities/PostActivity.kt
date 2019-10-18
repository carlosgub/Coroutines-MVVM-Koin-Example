package com.carlosgub.coroutines.features.books.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlosgub.coroutines.R
import com.carlosgub.coroutines.features.books.presentation.adapters.RVPostAdapter
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.post_activity.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostActivity : AppCompatActivity(),RVPostAdapter.Listener {

    private val viewModel:PostViewModel by viewModel()
    private val mAdapter = RVPostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_activity)

        mAdapter.setListener(this)

        rvPost.apply {
            layoutManager = LinearLayoutManager(this@PostActivity)
            adapter = mAdapter
        }

        lifecycleScope.launch {
            val posts = viewModel.getPosts()
            mAdapter.addAll(posts)
        }
    }

    override fun onPostClicked(id: Int) {
        startActivity(Intent(this,PostDetailActivity::class.java).apply {
            putExtras(Bundle().apply {
                putInt("ID",id)
            })
        })
    }
}
