package com.bugrahankaramollaoglu.sentensearch.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import com.bugrahankaramollaoglu.sentensearch.Fragments.BookmarksFragment
import com.bugrahankaramollaoglu.sentensearch.Fragments.SettingsFragment
import com.bugrahankaramollaoglu.sentensearch.Fragments.StartFragment
import com.bugrahankaramollaoglu.sentensearch.Models.SharedViewModel
import com.bugrahankaramollaoglu.sentensearch.R
import com.bugrahankaramollaoglu.sentensearch.databinding.ActivityMainBinding
import com.bugrahankaramollaoglu.sentensearch.Helpers.launchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isOpenedBefore: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences
    private var currentLang = "en"
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        if (sharedViewModel.isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        loadOpenedInformation()
        loadLangInformation()

        handleBottomNavPadding()

        if (!isOpenedBefore) {

            val intent = Intent(this@MainActivity, OnboardingActivity::class.java)
            startActivity(intent)
            val editor = sharedPreferences.edit()
            editor.putBoolean("is-opened-before-key", true)
            editor.apply()
        } else {
            binding.bottomNav.selectedItemId = R.id.homeItem
            launchFragment(supportFragmentManager, StartFragment(), false)
        }

        binding.bottomNav.setOnItemSelectedListener {

            val currentFragment = supportFragmentManager.findFragmentById(R.id.container)

            val isSameFragment = when (it.itemId) {
                R.id.bookmarksItem -> currentFragment is BookmarksFragment
                R.id.homeItem -> currentFragment is StartFragment
                R.id.settingsItem -> currentFragment is SettingsFragment
                else -> false
            }

            if (!isSameFragment) {
                when (it.itemId) {
                    R.id.bookmarksItem -> launchFragment(
                        supportFragmentManager, BookmarksFragment()
                    )

                    R.id.homeItem -> launchFragment(supportFragmentManager, StartFragment())
                    R.id.settingsItem -> launchFragment(supportFragmentManager, SettingsFragment())
                    else -> {
                        Toast.makeText(this@MainActivity, "clicked else", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            !isSameFragment

        }

        onBackPressedDispatcher.addCallback(this) {
            val fragmentManager = supportFragmentManager
            if (fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStack()
            } else {
                finish()
            }
        }
    }

    private fun handleBottomNavPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.bottomNav.updatePadding(bottom = systemBarsInsets.bottom)
            insets
        }
    }

    private fun loadOpenedInformation() {
        isOpenedBefore = sharedPreferences.getBoolean("is-opened-before-key", false)
    }

    private fun loadLangInformation() {
        currentLang = sharedPreferences.getString("current-lang-key", "en").toString()
    }

    fun hideBottomNavigation() {
        binding.bottomNav.visibility = View.GONE
    }

    fun showBottomNavigation() {
        binding.bottomNav.visibility = View.VISIBLE
    }
}
