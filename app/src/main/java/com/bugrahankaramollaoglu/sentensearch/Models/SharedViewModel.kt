package com.bugrahankaramollaoglu.sentensearch.Models

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.bugrahankaramollaoglu.sentensearch.HistoryAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private var historyAdapter: HistoryAdapter? = null

    fun setHistoryAdapter(adapter: HistoryAdapter) {
        historyAdapter = adapter
    }

    private val PREF_NAME = "com.bugrahankaramollaoglu.sentensearch"

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var isFetchedOnce: Boolean
        get() = sharedPreferences.getBoolean("is-fetched-key", false)
        set(value) = sharedPreferences.edit().putBoolean("is-fetched-key", value).apply()

    var isVibration: Boolean
        get() = sharedPreferences.getBoolean("is-vibration-key", true)
        set(value) = sharedPreferences.edit().putBoolean("is-vibration-key", value).apply()

    var isDark: Boolean
        get() = sharedPreferences.getBoolean("is-dark-key", true)
        set(value) = sharedPreferences.edit().putBoolean("is-dark-key", value).apply()

    var currentLang: String?
        get() = sharedPreferences.getString("current-lang-key", "tr")
        set(value) {
            sharedPreferences.edit().putString("current-lang-key", value).apply()
        }

    var bookmarkedSentences: MutableList<Sentence>
        get() {
            val json = sharedPreferences.getString("bookmarkedList-key", null)
            val type = object : TypeToken<List<Sentence>>() {}.type
            return Gson().fromJson(json, type) ?: mutableListOf()
        }
        set(value) {
            val json = Gson().toJson(value)
            sharedPreferences.edit().putString("bookmarkedList-key", json).apply()
        }

    fun addBookmarkedSentence(sentence: Sentence) {
        val currentList = bookmarkedSentences.toMutableList()
        currentList.add(sentence)
        bookmarkedSentences = currentList
        saveBookmarkedSentences()
    }

    fun removeBookmarkedSentence(sentence: Sentence) {
        val currentList = bookmarkedSentences.toMutableList()
        currentList.remove(sentence)
        bookmarkedSentences = currentList
        saveBookmarkedSentences()
    }

    fun clearBookmarkedSentences() {
        bookmarkedSentences = mutableListOf()
        saveBookmarkedSentences()
    }

    fun saveBookmarkedSentences() {
        val json = Gson().toJson(bookmarkedSentences)
        sharedPreferences.edit().putString("bookmarkedList-key", json).apply()
    }

    var history: MutableList<String>?
        get() = sharedPreferences.getStringSet("history-key", mutableSetOf())?.toMutableList()
        set(value) {
            sharedPreferences.edit().putStringSet("history-key", value?.toMutableSet()).apply()
        }

    fun addToHistory(item: String) {
        val currentHistory = history?.toMutableList() ?: mutableListOf()

        currentHistory.add(0, item)

        if (currentHistory.size > 5) {
            currentHistory.removeAt(currentHistory.size - 1)
        }

        history = currentHistory

        historyAdapter?.notifyDataSetChanged()
    }

}
