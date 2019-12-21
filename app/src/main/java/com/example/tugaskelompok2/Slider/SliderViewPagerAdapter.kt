package com.android.tugasmvc.ScreenSlider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.example.tugaskelompok2.R
import com.example.tugaskelompok2.Slider.ScreenItem

class SliderViewPagerAdapter(
    private var mContext: Context,
    private var mListScreen: List<ScreenItem>
) : PagerAdapter() {

    override fun getCount(): Int {
        return mListScreen.size
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutScreen = inflater.inflate(R.layout.layout_screen, null)

        val imgSlide = layoutScreen.findViewById<ImageView>(R.id.intro_img)

        imgSlide.setImageResource(mListScreen[position].screenImg)

        container.addView(layoutScreen)

        return layoutScreen
    }


    override fun isViewFromObject(@NonNull view: View, @NonNull o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {

        container.removeView(`object` as View)

    }
}