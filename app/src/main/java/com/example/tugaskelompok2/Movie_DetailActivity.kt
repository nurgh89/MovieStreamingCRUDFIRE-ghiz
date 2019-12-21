package com.example.tugaskelompok2

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class Movie_DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_movie_detail)

        val videoView = findViewById<VideoView>(R.id.videoPlayer)
        //Creating MediaController
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        //specify the location of media file
        val str: String=intent.getStringExtra("film")
        val uri: Uri = Uri.parse(str)
        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri)
        videoView.requestFocus()

        val textNama = findViewById<TextView>(R.id.namaFilm)
        val textDirector = findViewById<TextView>(R.id.director)
        val textReleased = findViewById<TextView>(R.id.released)
        val textSynopsis = findViewById<TextView>(R.id.Synopsis)
        val filmImage2 = findViewById<ImageView>(R.id.poster2)

        textNama.setText(intent.getStringExtra("name"))
        textDirector.setText(intent.getStringExtra("director"))
        textReleased.setText(intent.getStringExtra("released"))
        textSynopsis.setText(intent.getStringExtra("synopsis"))
        Picasso.get().load(intent.getStringExtra("image")).into(filmImage2)


    }
}