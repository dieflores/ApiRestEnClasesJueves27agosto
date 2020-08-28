package com.example.restapi

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.restapi.model.pojo.Post
import com.example.restapi.model.remote.RetrofitClient
import com.example.restapi.model.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_post_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var postsList = ArrayList<Post>()
    private lateinit var viewAdapter: PostAdapter
    private lateinit var mViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // INICIANDO VIEW MODEL
        mViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        // INICIANDO ADAPTER
        viewAdapter = PostAdapter(postsList)
        postsRecyclerView.adapter = viewAdapter

        // BOTON FAB AÑADIR NUEVO POST
        addpost.setOnClickListener {
            showDialog()
        }

        mViewModel.fetchFromServer()
        mViewModel.getDataFromDataB().observe(this, Observer { viewAdapter.upDateData(it) })

    }



    private fun makePost(post: Post) {
        val service = RetrofitClient.retrofitInstance()
        val call = service.createNewPost(post)
        call.enqueue((object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                when (response.code()) {
                    in 200..299 -> {
                        Toast.makeText(
                            this@MainActivity, "Post Realizado", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("MAIN", t.message.toString())

            }
        }))
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.new_post_dialog)

        dialog.sendBtn.setOnClickListener {
            val title = dialog.titleEt.text.toString()
            val body = dialog.bodyEt.text.toString()
            val userId = dialog.userIDET.text.toString().toLong()
            val post = Post(userId.toLong(), 100, title, body)
            makePost(post)
            dialog.dismiss()
        }
        dialog.show()
    }
}
