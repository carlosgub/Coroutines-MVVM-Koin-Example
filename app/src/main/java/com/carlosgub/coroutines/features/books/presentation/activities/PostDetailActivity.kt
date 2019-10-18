package com.carlosgub.coroutines.features.books.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.carlosgub.coroutines.R
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostDetailViewModel
import kotlinx.android.synthetic.main.post_detail_activity.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailActivity : AppCompatActivity() {

    private val viewModel : PostDetailViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_detail_activity)
        getExtras()
    }

    private fun getExtras(){
        val extras = intent.extras
        if(extras!=null){
            lifecycleScope.launch {
                val post = viewModel.getPostById(extras.getInt("ID",-1).toString())
                tvPostDetailId.text = "${post.id}"
                tvPostDetailBody.text = post.body
                tvPostDetailTitle.text = post.title
                tvPostDetailUserId.text = "${post.userId}"
            }
        }
    }
}
