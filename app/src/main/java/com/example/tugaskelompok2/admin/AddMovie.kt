package com.example.tugaskelompok2.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugaskelompok2.R
import com.example.tugaskelompok2.model.Film
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_movie.*
import java.util.*

class AddMovie : AppCompatActivity() {
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        ref = FirebaseDatabase.getInstance().getReference("Movie")

        buttonVideo.setOnClickListener{
            val intent= Intent(Intent.ACTION_PICK)
            intent.type="video/*"
            startActivityForResult(intent,0)
        }

        buttonSave.setOnClickListener {
//            val filmName = filmName.text.toString()
//            val filmDirector = filmDirector.text.toString()
//            val filmReleased = filmReleased.text.toString()
//            val filmSynopsis = filmSynopsis.text.toString()
//            if (filmName.isEmpty() || filmDirector.isEmpty() || filmReleased.isEmpty() || filmSynopsis.isEmpty()) {
//                Toast.makeText(this, "Please Insert Email and Password", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            } else {
                uploadVideoToFirbaseStorage()
                Toast.makeText(this, "Succesfully Added", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()
//            }
        }

    }


    var selectedVideoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data != null){
            selectedVideoUri= data.data

//            val bitmap= MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
//
//            selected_photo.setImageBitmap(bitmap)

            buttonVideo.alpha=0f
//            val bitmapDrawable= BitmapDrawable(bitmap)
//            select_photo.setBackgroundDrawable(bitmapDrawable)
        }


    }

    private fun uploadVideoToFirbaseStorage(){
        if (selectedVideoUri == null) return
        val filename= UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/Video/$filename")

        ref.putFile(selectedVideoUri!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    val video=it.toString()
                    saveMovieToFirebaseDatabase(video)
                }
            }

    }

    private fun saveMovieToFirebaseDatabase(videoUrl: String){
        val name=intent.getStringExtra("film_name")
        val director=intent.getStringExtra("director")
        val released=intent.getStringExtra("released")
        val synopsis=intent.getStringExtra("synopsis")
        val image=intent.getStringExtra("image")

        val id= ref.push().key.toString()

        val ref = FirebaseDatabase.getInstance().getReference("/Movie/")
        val user=
            Film(id ,name, director, released, synopsis,image,videoUrl)

        ref.child(id).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"Saved Succesfully", Toast.LENGTH_SHORT).show()
            }
    }
}