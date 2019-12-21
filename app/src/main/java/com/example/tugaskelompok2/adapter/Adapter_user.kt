package com.example.tugaskelompok2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.tugaskelompok2.Movie_DetailActivity
import com.example.tugaskelompok2.R
import com.example.tugaskelompok2.model.Film
import com.squareup.picasso.Picasso

class Adapter_user(val mCtx: Context, val layoutResId: Int, val list: List<Film> )
    : ArrayAdapter<Film>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val filmTitle = view.findViewById<TextView>(R.id.filmName)
        val filmImage = view.findViewById<ImageView>(R.id.poster)

//        val textUpdate = view.findViewById<TextView>(R.id.update)
//        val textDelete = view.findViewById<TextView>(R.id.delete)

        val film = list[position]

        filmTitle.text = film.nameFilm
        val url=film.image
        Picasso.get().load(url).into(filmImage)

        filmImage.setOnClickListener {
            val intent = Intent(it.context, Movie_DetailActivity::class.java)
            intent.putExtra("name",film.nameFilm)
            intent.putExtra("director",film.director)
            intent.putExtra("released",film.released_date)
            intent.putExtra("synopsis",film.synopsis)
            intent.putExtra("image",film.image)
            intent.putExtra("film",film.film)
            it.context.startActivity(intent)
        }


        return view

    }
}