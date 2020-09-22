package com.carlosgub.coroutines.features.books.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlosgub.coroutines.R
import com.carlosgub.coroutines.features.books.presentation.adapters.RVPostAdapter
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostsViewModel
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostsVS
import kotlinx.android.synthetic.main.posts_activity.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsActivity : AppCompatActivity(), RVPostAdapter.Listener {

    private val viewModel: PostsViewModel by viewModel()
    private val mAdapter = RVPostAdapter()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.posts_activity)
        observeViewModel()
        mAdapter.setListener(this)

        rvPost.apply {
            layoutManager = LinearLayoutManager(this@PostsActivity)
            adapter = mAdapter
        }

        viewModel.getPosts()
    }

    private fun observeViewModel(){
        //Obtener los Posts
        viewModel.viewState.observe(this@PostsActivity, {
            when (it) {
                is PostsVS.AddPost -> {
                    mAdapter.add(it.postVM)
                }
                is PostsVS.ShowLoader ->{
                    if(it.showLoader){
                        pbPosts.visibility = View.VISIBLE
                        rvPost.visibility = View.GONE
                    }else{
                        pbPosts.visibility = View.GONE
                        rvPost.visibility = View.VISIBLE
                    }
                }
                is PostsVS.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun onPostClicked(id: Int) {
        startActivity(PostActivity.createInstance(context = this, id = id.toString()))
    }
}
