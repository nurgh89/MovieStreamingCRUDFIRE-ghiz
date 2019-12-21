package com.example.tugaskelompok2.register_login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugaskelompok2.MainActivity
import com.example.tugaskelompok2.R
import com.example.tugaskelompok2.Slider.SliderActivity
import com.example.tugaskelompok2.admin.AdminActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener {
            val email = userEmail.text.toString()
            val password = userPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please Insert Email,Password, and Username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (email == "admin01@gmail.com" || password == "admin012") {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{

                        if (!it.isSuccessful){ return@addOnCompleteListener
                            val intent = Intent (this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else {
                            Toast.makeText(this, "Succesfully Login", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, AdminActivity::class.java)
                            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    .addOnFailureListener{
                        Log.d("Main", "Failed Login: ${it.message}")
                        Toast.makeText(this, "Email/Password incorrect", Toast.LENGTH_SHORT).show()
                    }
            }   else{
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{

                        if (!it.isSuccessful){ return@addOnCompleteListener
                            val intent = Intent (this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else {
                            Toast.makeText(this, "Succesfully Login", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, SliderActivity::class.java)
                            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    .addOnFailureListener{
                        Log.d("Main", "Failed Login: ${it.message}")
                        Toast.makeText(this, "Email/Password incorrect", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        textRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}