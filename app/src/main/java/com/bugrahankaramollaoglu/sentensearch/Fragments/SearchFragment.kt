package com.bugrahankaramollaoglu.sentensearch.Fragments

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bugrahankaramollaoglu.sentensearch.Activities.MainActivity
import com.bugrahankaramollaoglu.sentensearch.Adapters.SentencesAdapter
import com.bugrahankaramollaoglu.sentensearch.Helpers.FlipHelper
import com.bugrahankaramollaoglu.sentensearch.Helpers.automaticallyOpenKeyboard
import com.bugrahankaramollaoglu.sentensearch.Helpers.calculateTotalResults
import com.bugrahankaramollaoglu.sentensearch.Helpers.clearHistory
import com.bugrahankaramollaoglu.sentensearch.Helpers.closeButtonAction
import com.bugrahankaramollaoglu.sentensearch.Helpers.fetchDefinition
import com.bugrahankaramollaoglu.sentensearch.Helpers.hideAll
import com.bugrahankaramollaoglu.sentensearch.Helpers.isInternetAvailable
import com.bugrahankaramollaoglu.sentensearch.Helpers.isQueryValid
import com.bugrahankaramollaoglu.sentensearch.Helpers.readJsonDataFromAssets
import com.bugrahankaramollaoglu.sentensearch.Helpers.shuffleButtonAction
import com.bugrahankaramollaoglu.sentensearch.Helpers.shuffleSentences
import com.bugrahankaramollaoglu.sentensearch.Helpers.vibrate
import com.bugrahankaramollaoglu.sentensearch.HistoryAdapter
import com.bugrahankaramollaoglu.sentensearch.Localizations.makeLocalizationsSearch
import com.bugrahankaramollaoglu.sentensearch.Models.Sentence
import com.bugrahankaramollaoglu.sentensearch.Models.SentenceSearcher
import com.bugrahankaramollaoglu.sentensearch.Models.SharedViewModel
import com.bugrahankaramollaoglu.sentensearch.Helpers.ThemeHelper
import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class SearchFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentSearchBinding
    private val themeHelper: ThemeHelper = ThemeHelper()
    private lateinit var filteredSentences: List<Sentence>
    private lateinit var sentenceSearcher: SentenceSearcher
    private lateinit var sentencesAdapter: SentencesAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var jsonData: String = ""
    private lateinit var flipHelper: FlipHelper
    private lateinit var textToSpeech: TextToSpeech
    private var isHistoryCalledBefore: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textToSpeech = TextToSpeech(requireContext(), this)

    }

    fun setSentences(query: String) {
        filteredSentences = sentenceSearcher.search(query)

        binding.totalResultTextView.text =
            calculateTotalResults(sharedViewModel.currentLang, filteredSentences)

        shuffleSentences(filteredSentences, sentencesAdapter)

        sentencesAdapter = SentencesAdapter(filteredSentences, query, textToSpeech, sharedViewModel)
        binding.sentencesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.sentencesRecyclerView.adapter = sentencesAdapter

        shuffleSentences(filteredSentences, sentencesAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goBackArrow.setOnClickListener {
            Log.d("mesaj","tıklanıyor ama")
            requireActivity().supportFragmentManager.popBackStack()
        }

        hideAll(binding)

        (activity as? MainActivity)?.hideBottomNavigation()

        automaticallyOpenKeyboard(binding, requireContext(), view)
        themeHelper.applyThemeSearch(sharedViewModel, binding, requireContext())

        binding.definitionCard.visibility = View.GONE

        historyAdapter = HistoryAdapter(sharedViewModel) { word ->
            if (!isHistoryCalledBefore) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(500) // Delay for 1 second
                    binding.searchView.setQuery(word, true)
                    isHistoryCalledBefore = true
                }
            } else {
                binding.searchView.setQuery(word, true)
            }
        }
        sharedViewModel.setHistoryAdapter(historyAdapter)

        binding.historyRecyclerView.adapter = historyAdapter

        binding.resetHistory.setOnClickListener {
            clearHistory(sharedViewModel, requireContext(), historyAdapter)
        }

        makeLocalizationsSearch(sharedViewModel.currentLang, binding)

        val textToSpeech = TextToSpeech(context, object : TextToSpeech.OnInitListener {
            override fun onInit(status: Int) {
                if (status == TextToSpeech.SUCCESS) {
                    // TextToSpeech is initialized successfully
                } else {
                    // Initialization failed
                }
            }
        })

        binding.shuffleButton.setOnClickListener {
            shuffleButtonAction(requireContext(), sentencesAdapter, binding, filteredSentences)
            if (sharedViewModel.isVibration) {
                vibrate(requireContext(), sharedViewModel)
            }
        }

        flipHelper = FlipHelper(requireContext(), view)

        binding.definitionCard.setOnClickListener {
            flipHelper.flipCards(binding)
        }

        binding.backCard.setOnClickListener {
            flipHelper.flipCards(binding)
        }

        val closeBtn =
            binding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)

        closeBtn.setOnClickListener {
            closeButtonAction(binding, flipHelper, view, requireContext(), sentencesAdapter)
        }

        if (!sharedViewModel.isFetchedOnce) {
            Log.d("mesaj", "fetched searhcer")
            jsonData = readJsonDataFromAssets("sentences_file.json", requireContext())
            sharedViewModel.isFetchedOnce = true
        }

        sentenceSearcher = SentenceSearcher(requireContext())

        sentencesAdapter = SentencesAdapter(listOf(), "", textToSpeech, sharedViewModel)

        binding.sentencesRecyclerView.adapter = sentencesAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                if (!isInternetAvailable(requireContext())) {
                    hideAll(binding)
                    binding.sentenceNotFoundImage.visibility = View.VISIBLE
                } else if (isQueryValid(query)) {

                    fetchDefinition(
                        query,
                        binding,
                        requireContext(),
                        viewLifecycleOwner.lifecycleScope,
                        textToSpeech,
                        sharedViewModel,
                        historyAdapter
                    )

                    if (sharedViewModel.isVibration) {
                        vibrate(requireContext(), sharedViewModel)
                    }

                    setSentences(query)

                    hideAll(binding)

                    if (filteredSentences.isEmpty()) {
                        binding.sentenceNotFoundImage.visibility = View.VISIBLE
                    } else {
                        binding.sentenceNotFoundImage.visibility = View.INVISIBLE
                    }

                    return false
                } else {
                    hideAll(binding)
                    binding.sentenceNotFoundImage.visibility = View.VISIBLE
                    return false
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as? MainActivity)?.showBottomNavigation()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set language (you can customize this based on your app's requirements)
            val result: Int = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Handle language not supported or missing data
            } else {
                // Text-to-Speech is initialized successfully
            }
        } else {
            // Initialization failed
        }
    }

}