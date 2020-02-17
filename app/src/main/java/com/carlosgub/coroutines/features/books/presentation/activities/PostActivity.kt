package com.carlosgub.coroutines.features.books.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlosgub.coroutines.R
import com.carlosgub.coroutines.databinding.PostActivityBinding
import com.carlosgub.coroutines.features.books.presentation.adapters.RVPostAdapter
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostViewModel
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostVS
import kotlinx.android.synthetic.main.post_activity.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostActivity : AppCompatActivity(), RVPostAdapter.Listener {

    private val viewModel: PostViewModel by viewModel()
    private val mAdapter = RVPostAdapter()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: PostActivityBinding = DataBindingUtil.setContentView(this, R.layout.post_activity)

        binding.postViewModel = viewModel
        binding.lifecycleOwner = this

        mAdapter.setListener(this)

        rvPost.apply {
            layoutManager = LinearLayoutManager(this@PostActivity)
            adapter = mAdapter
        }

        //Obtener los Posts
        viewModel.viewState.observe(this@PostActivity, Observer {
            when (it) {
                is PostVS.AddPost -> {
                    mAdapter.add(it.postVM)
                }
                is PostVS.Error -> {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            }

        })

        viewModel.getPosts()
    }


    override fun onPostClicked(id: Int) {
        /*V2*/

    }
}
