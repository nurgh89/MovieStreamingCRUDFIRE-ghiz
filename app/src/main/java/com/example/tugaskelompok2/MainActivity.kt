package com.example.tugaskelompok2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.ListView
import androidx.viewpager.widget.ViewPager
import com.example.tugaskelompok2.SlideHome.Slide
import com.example.tugaskelompok2.SlideHome.SliderPagerAdapter
import com.example.tugaskelompok2.adapter.Adapter_user
import com.example.tugaskelompok2.model.Film
import com.example.tugaskelompok2.register_login.LoginActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Film>
    private lateinit var lstSlides: MutableList<Slide>
    lateinit var listView: GridView
    private lateinit var sliderpager: ViewPager
    private lateinit var indicator: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        ref = FirebaseDatabase.getInstance().getReference("Movie")
        list = mutableListOf()
        listView = findViewById(R.id.listViewTask)

        sliderpager = findViewById(R.id.slider_pager)
        indicator = findViewById(R.id.indicator)

        // prepare a list of slides ..
        lstSlides = arrayListOf()
        lstSlides.add(Slide(R.drawable.parasitebaru, "Parasite \nKorean Movie 2019"))
        lstSlides.add(Slide(R.drawable.jumanji, "Jumanji\nHollywood Movie  2019"))
        lstSlides.add(Slide(R.drawable.birdofpraybaru, "Birt Of Pray \nHollywood Movie 2019"))
        lstSlides.add(Slide(R.drawable.turningbaru_, "Turning \nHollywood Movie 2019"))
        val adapter = SliderPagerAdapter(this, lstSlides)
        sliderpager.setAdapter(adapter)


        // setup timer
        val timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(), 4000, 6000)
        indicator.setupWithViewPager(sliderpager, true)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val film = h.getValue(Film::class.java)
                        list.add(film!!)
                    }
                    val adapter = Adapter_user(this@MainActivity,R.layout.movie_row_user,list)
                    listView.adapter = adapter
                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.buttonLogout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent (this, LoginActivity::class.java)
                intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    internal inner class SliderTimer : TimerTask() {
        override fun run() {
            runOnUiThread {
                if (sliderpager!!.currentItem < lstSlides!!.size - 1) {
                    sliderpager!!.currentItem = sliderpager!!.currentItem + 1
                } else sliderpager!!.currentItem = 0
            }
        }
    }
}
