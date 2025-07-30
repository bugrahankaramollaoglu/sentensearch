package com.bugrahankaramollaoglu.sentensearch

import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bugrahankaramollaoglu.sentensearch.Helpers.vibrate
import com.bugrahankaramollaoglu.sentensearch.Models.SharedViewModel
import com.bugrahankaramollaoglu.sentensearch.databinding.RecyclerRowBinding
import java.util.Locale

class BookmarksAdapter(
    private val sharedViewModel: SharedViewModel, private val textToSpeech: TextToSpeech
) : RecyclerView.Adapter<BookmarksAdapter.BookmarkHolder>() {

    private var listener: OnAllUnbookmarkedListener? = null

    fun notifyAllUnbookmarked() {
        if (sharedViewModel.bookmarkedSentences.isEmpty()) {
            listener?.onAllUnbookmarked()
        }
    }

    interface OnAllUnbookmarkedListener {
        fun onAllUnbookmarked()
    }

    fun setOnAllUnbookmarkedListener(listener: OnAllUnbookmarkedListener) {
        this.listener = listener
    }

    class BookmarkHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    private var selectedItemPosition = RecyclerView.NO_POSITION


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val bookmarkDrawableRes = if (sharedViewModel.isDark == true) {
            R.drawable.baseline_bookmark_24
        } else {
            R.drawable.bookmark_light
        }
        binding.recyclerViewBookmark.setImageResource(bookmarkDrawableRes)

        return BookmarkHolder(binding)

    }

    override fun getItemCount(): Int {
        return sharedViewModel.bookmarkedSentences.size
    }

    private fun toggleTranslationCardVisibility(holder: BookmarksAdapter.BookmarkHolder) {
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

    override fun onBindViewHolder(holder: BookmarkHolder, position: Int) {
        val bsentence = sharedViewModel.bookmarkedSentences[position]


        holder.binding.recyclerViewNumber.text = "${position + 1}."
        holder.binding.recyclerViewTextView.text = "${bsentence.sentence}."

        holder.binding.translationCard.visibility = View.GONE

        holder.binding.recyclerViewBookmark.setOnClickListener {

            if (sharedViewModel.isVibration) {
                vibrate(holder.itemView.context, sharedViewModel)
            }

            sharedViewModel.removeBookmarkedSentence(bsentence)

            if (sharedViewModel.bookmarkedSentences.isEmpty()) {
                notifyAllUnbookmarked()
            }
            notifyDataSetChanged()

        }

        holder.binding.recyclerViewTextView.setOnLongClickListener {
            textToSpeech.language = Locale.ENGLISH
            textToSpeech.speak(
                sharedViewModel.bookmarkedSentences[position].sentence,
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
            true
        }

        holder.binding.recyclerViewTextView.setOnClickListener {
            if (sharedViewModel.currentLang in listOf("de", "fr", "tr", "ru", "pt", "es")) {
                toggleTranslationCardVisibility(holder)
            }
        }


        holder.binding.translationCard.visibility = if (position == selectedItemPosition) {
            View.VISIBLE
        } else {
            View.GONE
        }

        when (sharedViewModel.currentLang) {
            "de" -> {
                holder.binding.translationText.text =
                    "${sharedViewModel.bookmarkedSentences[position].toGerman}."
            }

            "fr" -> {
                holder.binding.translationText.text =
                    "${sharedViewModel.bookmarkedSentences[position].toFrench}."
            }

            "tr" -> {
                holder.binding.translationText.text =
                    "${sharedViewModel.bookmarkedSentences[position].toTurkish}."
            }

            "es" -> {
                holder.binding.translationText.text =
                    "${sharedViewModel.bookmarkedSentences[position].toSpanish}."
            }

            "ru" -> {
                holder.binding.translationText.text =
                    "${sharedViewModel.bookmarkedSentences[position].toRussian}."
            }

            "pt" -> {
                holder.binding.translationText.text =
                    "${sharedViewModel.bookmarkedSentences[position].toPortuguese}."
            }
        }
    }


}