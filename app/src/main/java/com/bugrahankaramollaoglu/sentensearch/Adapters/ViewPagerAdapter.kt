package com.bugrahankaramollaoglu.sentensearch.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bugrahankaramollaoglu.sentensearch.R

class ViewPagerAdapter(private val context: Context) : PagerAdapter() {

    private val images = arrayOf(
        R.drawable.image0,
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6,
        R.drawable.image7,
    )

    private val headings = arrayOf(
        R.string.heading_zero,
        R.string.heading_one,
        R.string.heading_two,
        R.string.heading_three,
        R.string.heading_four,
        R.string.heading_five,
        R.string.heading_six
    )

    private val descriptions = arrayOf(
        R.string.desc_zero,
        R.string.desc_one,
        R.string.desc_two,
        R.string.desc_three,
        R.string.desc_four,
        R.string.desc_five,
        R.string.desc_six
    )

    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_layout, container, false)

        val slideTitleImage = view.findViewById<ImageView>(R.id.onboardImage_1)
        val slideHeading = view.findViewById<TextView>(R.id.onboardTitle_1)
        val slideDescription = view.findViewById<TextView>(R.id.onboardText_1)
        val slideExtraImageContainer = view.findViewById<LinearLayout>(R.id.extraImageContainer)

        slideTitleImage.setImageResource(images[position])
        slideHeading.setText(headings[position])
        slideDescription.setText(descriptions[position])

        if (position == 0) {
            val layoutParams = slideTitleImage.layoutParams
            layoutParams.width = 650
            layoutParams.height = 650
            slideTitleImage.layoutParams = layoutParams
        }

        if (position == 4) {
            val extraImage = ImageView(context)
            extraImage.setImageResource(R.drawable.image7)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            extraImage.layoutParams = layoutParams

            slideExtraImageContainer.addView(extraImage)
        }

        container.addView(view)
        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}
