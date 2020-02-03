package com.carlosgub.coroutines.features.books.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.carlosgub.coroutines.R
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostDetailViewModel
import com.carlosgub.coroutines.features.books.presentation.viewmodel.viewstate.PostDetailVS
import kotlinx.android.synthetic.main.post_detail_activity.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailActivity : AppCompatActivity() {

    companion object {
        fun getInstance(context: Context, id: Int) :Intent =
            Intent(context, PostDetailActivity::class.java).apply {
                putExtras(Bundle().apply {
                    putInt("ID", id)
                })
            }

    }

    private val viewModel: PostDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_detail_activity)
        getViewModelComplete()
        getExtras()
    }

    private fun getExtras() {
        val extras = intent.extras
        if (extras != null) {
            lifecycleScope.launch {
                viewModel.getPostById(extras.getInt("ID", -1).toString())
                pbPostDetail.visibility = View.GONE
            }
        }
    }


    private fun getViewModelComplete() {
        viewModel.state.observe(this, Observer {
            when (it) {
                is PostDetailVS.Loading -> {
                    pbPostDetail.visibility = if (it.show) View.VISIBLE else View.GONE
                }

                is PostDetailVS.GetPost -> {
                    val post = it.post
                    tvPostDetailId.text = "${post.id}"
                    tvPostDetailBody.text = post.body
                    tvPostDetailTitle.text = post.title
                    tvPostDetailUserId.text = "${post.userId}"
                }
            }
        })
    }
}
