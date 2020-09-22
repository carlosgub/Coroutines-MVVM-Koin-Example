package com.carlosgub.coroutines.features.books.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.carlosgub.coroutines.R
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostViewModel
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostVS
import kotlinx.android.synthetic.main.post_activity.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModel()
    private lateinit var id: String

    companion object {
        private const val ARG_ID = "arg_id"
        fun createInstance(context: Context, id: String): Intent {
            return Intent(context, PostActivity::class.java).putExtras(
                Bundle().apply {
                    putString(ARG_ID, id)
                }
            )
        }
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_activity)
        observeViewModel()
        intent.extras.let {
            id = it?.getString(ARG_ID) ?: "-1"
            viewModel.getPostByID(id)
        }

    }

    private fun observeViewModel() {
        viewModel.viewState.observe(this@PostActivity, {
            when (it) {
                is PostVS.Post -> {
                    tvPostITitle.text = String.format(getString(R.string.post_text),it.postVM.title,it.postVM.body)
                }
                is PostVS.ShowLoader ->{
                    if(it.showLoader){
                        pbPost.visibility = View.VISIBLE
                        gPost.visibility = View.GONE
                    }else{
                        pbPost.visibility = View.GONE
                        gPost.visibility = View.VISIBLE
                    }
                }
                is PostVS.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
