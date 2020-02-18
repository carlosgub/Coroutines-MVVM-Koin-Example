package com.carlosgub.coroutines.features.books.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlosgub.coroutines.databinding.PostActivityBinding
import com.carlosgub.coroutines.features.books.presentation.adapters.RVPostAdapter
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostViewModel
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostVS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostActivity : AppCompatActivity(), RVPostAdapter.Listener {

    private val viewModel: PostViewModel by viewModel()
    private val _adapter = RVPostAdapter()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val binding: PostActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.post_activity)*/

        val binding = PostActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.postViewModel = viewModel

        _adapter.setListener(this)

        binding.rvPost.apply {
            layoutManager = LinearLayoutManager(this@PostActivity)
            adapter = _adapter
        }

        //Obtener los Posts
        viewModel.viewState.observe(this@PostActivity, Observer {
            when (it) {
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
