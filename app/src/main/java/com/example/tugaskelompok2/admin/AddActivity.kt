package com.example.tugaskelompok2.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.tugaskelompok2.R
import com.example.tugaskelompok2.model.Film
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.filmName
import java.util.*

class AddActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        ref = FirebaseDatabase.getInstance().getReference("Movie")

        buttonImage.setOnClickListener{
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }

        buttonAdd.setOnClickListener {
            val filmName = filmName.text.toString()
            val filmDirector = filmDirector.text.toString()
            val filmReleased = filmReleased.text.toString()
            val filmSynopsis = filmSynopsis.text.toString()
            if (filmName.isEmpty() || filmDirector.isEmpty() || filmReleased.isEmpty() || filmSynopsis.isEmpty()) {
                Toast.makeText(this, "Please Insert Email and Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                uploadImageToFirbaseStorage()
            }
        }

    }



    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data != null){
            selectedPhotoUri= data.data

            val bitmap= MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            selected_photo.setImageBitmap(bitmap)

            buttonImage.alpha=0f
//            val bitmapDrawable= BitmapDrawable(bitmap)
//            select_photo.setBackgroundDrawable(bitmapDrawable)
        }


    }

    private fun uploadImageToFirbaseStorage(){
        if (selectedPhotoUri == null) return
        val filename= UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/Movie/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    val image=it.toString()
                    saveMovieToFirebaseDatabase(image,image)
                }
            }

    }

    private fun saveMovieToFirebaseDatabase(profileImageUrl: String, videoUrl: String){
        val name=filmName.text.toString()
        val director=filmDirector.text.toString()
        val released=filmReleased.text.toString()
        val synopsis=filmSynopsis.text.toString()

        val intent = Intent(this, AddMovie::class.java)
        intent.putExtra("image",profileImageUrl)
        intent.putExtra("video",videoUrl)
        intent.putExtra("film_name",name)
        intent.putExtra("director",director)
        intent.putExtra("released",released)
        intent.putExtra("synopsis",synopsis)
        startActivity(intent)
        finish()
    }


}


