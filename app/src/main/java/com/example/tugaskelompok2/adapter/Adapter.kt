package com.example.tugaskelompok2.adapter

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.tugaskelompok2.R
import com.example.tugaskelompok2.admin.AdminActivity
import com.example.tugaskelompok2.model.Film
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add.*

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<Film> )
    : ArrayAdapter<Film>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val filmTitle = view.findViewById<TextView>(R.id.filmName)
        val filmReleased = view.findViewById<TextView>(R.id.released_date)
        val filmDirected = view.findViewById<TextView>(R.id.director)
        val filmSynopsis = view.findViewById<TextView>(R.id.synopsis)
        val filmImage = view.findViewById<ImageView>(R.id.poster)

        val textUpdate = view.findViewById<TextView>(R.id.update)
        val textDelete = view.findViewById<TextView>(R.id.delete)

        val film = list[position]

        filmTitle.text = film.nameFilm
        filmReleased.text = film.released_date
        filmDirected.text = film.director
        filmSynopsis.text = film.synopsis
        val url=film.image
        Picasso.get().load(url).into(filmImage)

        textUpdate.setOnClickListener {
            showUpdateDialog(film)
        }
        textDelete.setOnClickListener {
            Deleteinfo(film)
        }

        return view

    }

    private fun Deleteinfo(film: Film) {

        val progressDialog = ProgressDialog(context,
            R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("Movie")
        mydatabase.child(film.id).removeValue()
        Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, AdminActivity::class.java)
        context.startActivity(intent)

    }

    private fun showUpdateDialog(film: Film) {

        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.activity_update, null)

        val textNama = view.findViewById<EditText>(R.id.filmName)
        val textDirector = view.findViewById<EditText>(R.id.filmDirector)
        val textReleased = view.findViewById<EditText>(R.id.filmReleased)
        val textSynopsis = view.findViewById<EditText>(R.id.filmSynopsis)
        val filmImage = view.findViewById<ImageView>(R.id.selected_photo)


        val url=film.image
        Picasso.get().load(url).into(filmImage)

        textNama.setText(film.nameFilm)
        textDirector.setText(film.director)
        textReleased.setText(film.released_date)
        textSynopsis.setText(film.synopsis)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbFilms = FirebaseDatabase.getInstance().getReference("Movie")

            val nama = textNama.text.toString().trim()

            val director = textDirector.text.toString().trim()

            val released = textReleased.text.toString().trim()

            val sysnopsis = textSynopsis.text.toString().trim()


            if (nama.isEmpty()){
                textNama.error = "please enter Film name"
                textNama.requestFocus()
                return@setPositiveButton
            }

            if (director.isEmpty()){
                textDirector.error = "please enter Director name"
                textDirector.requestFocus()
                return@setPositiveButton
            }

            if (released.isEmpty()){
                textDirector.error = "please enter released Date"
                textDirector.requestFocus()
                return@setPositiveButton
            }

            if (sysnopsis.isEmpty()){
                textDirector.error = "please enter sysnopsis"
                textDirector.requestFocus()
                return@setPositiveButton
            }

            val film = Film(film.id,nama,director,released,sysnopsis,film.image,film.film)

            dbFilms.child(film.id).setValue(film).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()

    }

}
