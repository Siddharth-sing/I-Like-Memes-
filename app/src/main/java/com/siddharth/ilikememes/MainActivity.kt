package com.siddharth.ilikememes

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    var currentImgUrl : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("Hello")
        load()
    }



    fun load()
    {
        val progressBar:ProgressBar=findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val urll = "https://meme-api.herokuapp.com/gimme"
        val imageMeme :ImageView = findViewById(R.id.imageMeme)

// Request a string response from the provided URL.
        println("Hello up Json")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, urll, null,
            { response ->
                println("Hello in Json")
                println(response)
                currentImgUrl = response.getString("url")
                Glide.with(this).load(currentImgUrl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }
                }).into(imageMeme)

            },
            {
                Toast.makeText(this,"Some Error Occurred",Toast.LENGTH_SHORT).show()
            }

        )
        queue.add(jsonObjectRequest)
 }
    fun shareMeme(view: View) {
        val intent:Intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Checkout this amazing meme $currentImgUrl")
        val chooser = Intent.createChooser(intent,"Share the meme using...")
        startActivity(chooser)
    }
    fun nxtMeme(view: View) {
        load()
      }
    }