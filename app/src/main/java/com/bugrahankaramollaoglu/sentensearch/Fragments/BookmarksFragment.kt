package com.bugrahankaramollaoglu.sentensearch.Fragments

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bugrahankaramollaoglu.sentensearch.Activities.MainActivity
import com.bugrahankaramollaoglu.sentensearch.BookmarksAdapter
import com.bugrahankaramollaoglu.sentensearch.Localizations.makeLocalizationsBookmark
import com.bugrahankaramollaoglu.sentensearch.Models.SharedViewModel
import com.bugrahankaramollaoglu.sentensearch.R
import com.bugrahankaramollaoglu.sentensearch.Helpers.ThemeHelper
import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentBookmarksBinding
import com.bugrahankaramollaoglu.sentensearch.Helpers.exportSentences
import com.bugrahankaramollaoglu.sentensearch.Helpers.launchFragment

class BookmarksFragment : Fragment(), BookmarksAdapter.OnAllUnbookmarkedListener {

    private lateinit var binding: FragmentBookmarksBinding
    private lateinit var bookmarksAdapter: BookmarksAdapter
    private lateinit var textToSpeech: TextToSpeech
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val themeHelper: ThemeHelper = ThemeHelper()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        val mainActivity = activity as MainActivity?
        val bottomNavDivider = mainActivity?.findViewById<View>(R.id.bottomNavDivider)
        bottomNavDivider?.visibility = View.VISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //////////////////////////////////////////////////////

        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
            } else {
            }
        }

        makeLocalizationsBookmark(sharedViewModel.currentLang, binding)
        themeHelper.applyThemeBookmarks(sharedViewModel, binding)

        binding.recyclerViewBookmarks.layoutManager = LinearLayoutManager(requireContext())
        bookmarksAdapter = BookmarksAdapter(
            sharedViewModel,
            textToSpeech
        )
        binding.recyclerViewBookmarks.adapter = bookmarksAdapter

        bookmarksAdapter.setOnAllUnbookmarkedListener(this)

        if (sharedViewModel.bookmarkedSentences.isNotEmpty()) {
            binding.booksImage.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.startSearchingCard.visibility = View.GONE
            binding.notFoundText.visibility = View.GONE
        }

        binding.exportSentences.setOnClickListener {
            exportSentences(sharedViewModel, requireContext())
        }

        binding.startSearchingCard.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            launchFragment(parentFragmentManager, SearchFragment())

        }
    }

    override fun onAllUnbookmarked() {
        binding.booksImage.visibility = View.VISIBLE
        binding.notFoundText.visibility = View.VISIBLE
        binding.startSearchingCard.visibility = View.VISIBLE
        Log.d("mesaj", "hepsi silindi")
    }


}