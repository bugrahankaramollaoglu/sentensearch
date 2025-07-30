package com.bugrahankaramollaoglu.sentensearch.Adapters

import android.content.Context
import android.graphics.Typeface
import android.speech.tts.TextToSpeech
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bugrahankaramollaoglu.sentensearch.Models.Sentence
import com.bugrahankaramollaoglu.sentensearch.Models.SharedViewModel
import com.bugrahankaramollaoglu.sentensearch.R
import com.bugrahankaramollaoglu.sentensearch.Helpers.ThemeHelper
import com.bugrahankaramollaoglu.sentensearch.databinding.RecyclerRowBinding
import com.bugrahankaramollaoglu.sentensearch.Helpers.vibrate
import java.util.Locale
import java.util.regex.Pattern

class SentencesAdapter(
    private var filteredSentences: List<Sentence>,
    private var query: String,
    private val textToSpeech: TextToSpeech,
    private val sharedViewModel: SharedViewModel

) :
    RecyclerView.Adapter<SentencesAdapter.SentenceHolder>() {

    private var selectedItemPosition = RecyclerView.NO_POSITION
    private var query2: String = ""
    private val themeHelper: ThemeHelper = ThemeHelper()
    private var isBookmarked: Boolean = false

    class SentenceHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentenceHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        themeHelper.applyThemeSentenceAdapter(sharedViewModel, binding, isBookmarked)

        return SentenceHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredSentences.size
    }

    override fun onBindViewHolder(holder: SentenceHolder, position: Int) {
        val sentence = filteredSentences[position]

        isBookmarked = sharedViewModel.bookmarkedSentences.contains(sentence)

        if (sharedViewModel.isDark) {
            if (isBookmarked) {
                holder.binding.recyclerViewBookmark.setImageResource(R.drawable.baseline_bookmark_24)
            } else {
                holder.binding.recyclerViewBookmark.setImageResource(R.drawable.outline_bookmark_border_24)
            }
        } else {
            if (isBookmarked) {
                holder.binding.recyclerViewBookmark.setImageResource(R.drawable.bookmark_light)
            } else {
                holder.binding.recyclerViewBookmark.setImageResource(R.drawable.unfilled_bookmark_light)
            }
        }

        holder.binding.recyclerViewNumber.text = "${position + 1}."
        holder.binding.recyclerViewTextView.text =
            formatSentence(holder.itemView.context, sentence.sentence, query)

        holder.binding.recyclerViewTextView.setOnClickListener {
            if (sharedViewModel.currentLang in listOf("de", "fr", "tr", "ru", "pt", "es")) {
                openTranslationCard(holder)
            }
        }

        holder.binding.recyclerViewBookmark.setOnClickListener {
            if (sharedViewModel.isVibration) {
                vibrate(holder.itemView.context, sharedViewModel)
            }

            val sentence = filteredSentences[position]
            val isCurrentlyBookmarked = sharedViewModel.bookmarkedSentences.contains(sentence)

            if (!isCurrentlyBookmarked) {
                sharedViewModel.addBookmarkedSentence(sentence)
            } else {
                sharedViewModel.removeBookmarkedSentence(sentence)
            }

            isBookmarked = !isCurrentlyBookmarked

            val bookmarkImageRes = if (isCurrentlyBookmarked) {
                if (sharedViewModel.isDark) R.drawable.outline_bookmark_border_24 else R.drawable.unfilled_bookmark_light
            } else {
                if (sharedViewModel.isDark) R.drawable.baseline_bookmark_24 else R.drawable.bookmark_light
            }
            holder.binding.recyclerViewBookmark.setImageResource(bookmarkImageRes)
        }


        holder.binding.recyclerViewTextView.setOnLongClickListener {
            textToSpeech.setLanguage(Locale.ENGLISH)
            textToSpeech.speak(sentence.sentence, TextToSpeech.QUEUE_FLUSH, null, null)
            true
        }

        holder.binding.translationCard.visibility = if (position == selectedItemPosition) {
            View.VISIBLE
        } else {
            View.GONE
        }


        when (sharedViewModel.currentLang) {
            "de" -> {
                holder.binding.translationText.text = "${filteredSentences[position].toGerman}."
            }

            "fr" -> {
                holder.binding.translationText.text =  "${filteredSentences[position].toFrench}."
            }

            "tr" -> {
                holder.binding.translationText.text =  "${filteredSentences[position].toTurkish}."
            }

            "es" -> {
                holder.binding.translationText.text =  "${filteredSentences[position].toSpanish}."
            }

            "ru" -> {
                holder.binding.translationText.text =  "${filteredSentences[position].toRussian}."
            }

            "pt" -> {
                holder.binding.translationText.text =  "${filteredSentences[position].toPortuguese}."
            }
        }
    }

    private fun openTranslationCard(holder: SentenceHolder) {
        val currentPosition = holder.adapterPosition
        if (currentPosition == selectedItemPosition) {
            holder.binding.translationCard.visibility =
                if (holder.binding.translationCard.visibility == View.VISIBLE) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            selectedItemPosition = RecyclerView.NO_POSITION
        } else {
            selectedItemPosition = currentPosition
            notifyDataSetChanged()
        }
    }

    private fun formatSentence(
        context: Context,
        sentence: String,
        query: String?
    ): SpannableString {

        var sentence2 = "${sentence}."

        val spannableString = SpannableString(sentence2)

        if (query != null) {
            query2 = query.lowercase()
        }

        if (!query.isNullOrBlank()) {
            val pattern = "\\b${Pattern.quote(query2)}\\b".toPattern()
            val matcher = pattern.matcher(sentence2.lowercase())

            while (matcher.find()) {
                val startIndex = matcher.start()
                val endIndex = matcher.end()

                spannableString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
                if (sharedViewModel.isDark) {
                    spannableString.setSpan(
                        ForegroundColorSpan(ContextCompat.getColor(context, R.color.color4)),
                        startIndex,
                        endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else {
                    spannableString.setSpan(
                        ForegroundColorSpan(ContextCompat.getColor(context, R.color.color10)),
                        startIndex,
                        endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }

        return spannableString
    }

    fun getDisplayedSentences(_displayedSentences: List<Sentence>) {
        filteredSentences = _displayedSentences
        notifyDataSetChanged()
    }

    fun resetSelectedItemPosition() {
        selectedItemPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }


}
