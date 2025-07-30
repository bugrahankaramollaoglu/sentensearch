package com.bugrahankaramollaoglu.sentensearch.Activities

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bugrahankaramollaoglu.sentensearch.Adapters.ViewPagerAdapter
import com.bugrahankaramollaoglu.sentensearch.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var mSLideViewPager: ViewPager
    private lateinit var mDotLayout: LinearLayout
    private lateinit var dots: Array<TextView>
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        mDotLayout = binding.indicatorLayout
        mSLideViewPager = binding.slideViewPager

        binding.backButton.setOnClickListener {
            if (getItem(0) > 0) {
                mSLideViewPager.setCurrentItem(getItem(-1), true)
            }
        }

        binding.nextButton.setOnClickListener {
            if (getItem(0) < 6) {
                mSLideViewPager.setCurrentItem(getItem(1), true)
            } else {
                val i = Intent(this@OnboardingActivity, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }

        binding.skipButton.setOnClickListener {
            val i = Intent(this@OnboardingActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        viewPagerAdapter = ViewPagerAdapter(this)
        binding.slideViewPager.adapter = viewPagerAdapter

        setUpIndicator(0)
        binding.slideViewPager.addOnPageChangeListener(viewListener)
    }

    private fun setUpIndicator(position: Int) {
        dots = Array(7) { TextView(this) }
        mDotLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i].text = Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY)
            dots[i].textSize = 18f
            dots[i].setTextColor(resources.getColor(R.color.darker_gray, applicationContext.theme))
            mDotLayout.addView(dots[i])
        }

        dots[position].setTextColor(Color.parseColor("#3D3870"))
    }

    private val viewListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            setUpIndicator(position)
            binding.backButtonCard.visibility = if (position > 0) View.VISIBLE else View.GONE

            if (position == viewPagerAdapter.count - 1) {
                binding.nextButton.text = "BAŞLA"
                binding.nextButtonCard.strokeColor = Color.parseColor("#F3DD4B")
                binding.nextButtonCard.strokeWidth = 3

                binding.skipCard.visibility = View.GONE
            } else {
                binding.nextButton.text = "İLERİ"
                binding.nextButtonCard.strokeColor = Color.parseColor("#000000")
                binding.nextButtonCard.strokeWidth = 1

                binding.skipCard.visibility = View.VISIBLE
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }


    private fun getItem(i: Int): Int {
        return mSLideViewPager.currentItem + i
    }

}