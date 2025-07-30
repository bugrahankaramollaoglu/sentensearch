package com.bugrahankaramollaoglu.sentensearch.Helpers

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import com.bugrahankaramollaoglu.sentensearch.HistoryAdapter
import com.bugrahankaramollaoglu.sentensearch.Models.SharedViewModel
import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentSearchBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

private var mediaPlayer: MediaPlayer? = null
private var lackSound = 0

fun hideAll(binding: FragmentSearchBinding) {
    binding.wordDetailsTextView.visibility = View.GONE
    binding.definitionCard.visibility = View.GONE
    binding.totalResultTextView.visibility = View.GONE
    binding.wordSentencesTextView.visibility = View.GONE
    binding.sentencesRecyclerView.visibility = View.GONE
    binding.shuffleButton.visibility = View.GONE
    binding.backCard.visibility = View.GONE
    binding.sentenceNotFoundImage.visibility = View.GONE
    binding.historyRecyclerView.visibility = View.VISIBLE
}

fun unhideAll(binding: FragmentSearchBinding) {
    binding.apply {
        binding.definitionCard.visibility = View.VISIBLE
        binding.totalResultTextView.visibility = View.VISIBLE
        binding.wordDetailsTextView.visibility = View.VISIBLE
        binding.wordSentencesTextView.visibility = View.VISIBLE
        binding.sentencesRecyclerView.visibility = View.VISIBLE
        binding.shuffleButton.visibility = View.VISIBLE
        binding.backCard.visibility = View.VISIBLE
    }
}

fun fetchDefinition(
    word: String?,
    binding: FragmentSearchBinding,
    context: Context,
    lifecycleScope: LifecycleCoroutineScope,
    textToSpeech: TextToSpeech,
    sharedViewModel: SharedViewModel,
    historyAdapter: HistoryAdapter
) {
    val apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/$word"

    val client = OkHttpClient()
    val request = Request.Builder().url(apiUrl).build()

    try {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    client.newCall(request).execute()
                }

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && isInternetAvailable(context)) {

                        val responseData = response.body?.string()
                        setDefinition(responseData, binding)
                        if (lackSound == 1) {
                            binding.soundWordImage.setOnClickListener {
                                textToSpeech.speak(
                                    binding.wordSubmittedTextView.text.toString(),
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    null
                                )
                            }
                        }

                        val lastFive = sharedViewModel.history?.takeLast(5)?.map { it.lowercase() }
                            ?: emptyList()

                        if (!lastFive.any { it.equals(word!!.trim(), ignoreCase = true) }) {
                            if (word != null) {
                                sharedViewModel.addToHistory(word.lowercase())
                                historyAdapter.notifyDataSetChanged() // Notify adapter after adding item to history
                            }
                            historyAdapter.notifyDataSetChanged()
                        }

                        unhideAll(binding)

                        binding.backCard.visibility = View.GONE
                        binding.definitionCard.visibility = View.VISIBLE
                        binding.historyRecyclerView.visibility = View.GONE
                        binding.resetHistory.visibility = View.GONE
                        binding.searchView.clearFocus()

                    } else {
                        hideAll(binding)
                        binding.sentenceNotFoundImage.visibility = View.VISIBLE
                        binding.historyRecyclerView.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    } catch (e: Exception) {
        Log.d("mesaj", "Exception: ${e.message}")
    }
}


fun setDefinition(wholeData: String?, binding: FragmentSearchBinding) {

    binding.adverbCardView.visibility = View.VISIBLE
    binding.adjectiveCardView.visibility = View.VISIBLE
    binding.nounCardView.visibility = View.VISIBLE
    binding.verbCardView.visibility = View.VISIBLE

    binding.synonymsText.text = ""
    binding.antonymsText.text = ""

    try {
        val jsonArray = JSONArray(wholeData)

        var synonymsSet = mutableSetOf<String>()
        var antonymsSet = mutableSetOf<String>()

        if (jsonArray.length() > 0) {
            val firstObject = jsonArray.getJSONObject(0)

            val partOfSpeechSet = mutableSetOf<String>()

            for (i in 0 until jsonArray.length()) {
                val wordObject = jsonArray.getJSONObject(i)
                extractPartOfSpeech(wordObject, partOfSpeechSet, synonymsSet, antonymsSet)
            }

            if (!partOfSpeechSet.contains("adverb")) {
                binding.adverbCardView.visibility = View.GONE
            }
            if (!partOfSpeechSet.contains("adjective")) {
                binding.adjectiveCardView.visibility = View.GONE
            }
            if (!partOfSpeechSet.contains("noun")) {
                binding.nounCardView.visibility = View.GONE
            }
            if (!partOfSpeechSet.contains("verb")) {
                binding.verbCardView.visibility = View.GONE
            }

            val word = firstObject.optString("word", "")

            var phonetic = firstObject.optString("phonetic", "")
            if (phonetic.isEmpty()) {
                val phoneticsArray = firstObject.optJSONArray("phonetics")
                if (phoneticsArray != null && phoneticsArray.length() > 1) {
                    val secondPhoneticObject = phoneticsArray.getJSONObject(1)
                    phonetic = secondPhoneticObject.optString("text", "")
                }
            }

            fun findFirstAudioUrl(jsonObject: JSONObject): String {
                val keys = jsonObject.keys()

                while (keys.hasNext()) {
                    val key = keys.next()
                    val value = jsonObject.get(key)

                    if (value is JSONObject) {
                        val audioUrl = findFirstAudioUrl(value)
                        if (audioUrl.isNotEmpty()) {
                            return audioUrl
                        } else {
                            lackSound = 1
                        }
                    } else if (value is JSONArray) {
                        for (i in 0 until value.length()) {
                            val arrayValue = value.get(i)
                            if (arrayValue is JSONObject) {
                                val audioUrl = findFirstAudioUrl(arrayValue)
                                if (audioUrl.isNotEmpty()) {
                                    return audioUrl
                                }
                            }
                        }
                    } else if (key == "audio" && value is String && value.isNotEmpty()) {
                        return value
                    }
                }


                return ""
            }

            val audioUrl = findFirstAudioUrl(firstObject)

            val definitionDataArray = firstObject.optJSONArray("meanings")
            if ((definitionDataArray?.length() ?: 0) > 0) {

                val firstMeaning = definitionDataArray!!.getJSONObject(0)
                val definitionsArray = firstMeaning.optJSONArray("definitions")

                if ((definitionsArray?.length() ?: 0) > 0) {
                    val firstDefinition = definitionsArray!!.getJSONObject(0)
                    val definitionText = firstDefinition.optString("definition", "")

                    phonetic = phonetic.substring(1, phonetic.length - 1)

                    binding.wordSubmittedTextView.text = word
                    binding.backWordSubmittedText.text = word
                    binding.phoneticTextView.text = phonetic
                    binding.definitionTextView.text = definitionText
                }
            }

            if (synonymsSet.isNotEmpty()) {
                synonymsSet = synonymsSet.take(2).toMutableSet()
                val synonymsText = "Synonyms: ${synonymsSet.joinToString(", ")}"
                binding.synonymsText.visibility = View.VISIBLE
                binding.synonymsText.text = synonymsText
            } else {
                binding.synonymsText.visibility = View.GONE
            }

            if (antonymsSet.isNotEmpty()) {
                antonymsSet = antonymsSet.take(2).toMutableSet()
                val antonymsText = "Antonyms: ${antonymsSet.joinToString(", ")}"
                binding.antonymsText.text = antonymsText
                binding.antonymsText.visibility = View.VISIBLE
            } else {
                binding.antonymsText.visibility = View.GONE
            }

            binding.soundWordImage.setOnClickListener {
                playAudio(audioUrl)
            }
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }
}

fun playAudio(audioUrl: String?) {
    mediaPlayer?.release()

    mediaPlayer = MediaPlayer()

    val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()

    mediaPlayer?.setAudioAttributes(audioAttributes)

    try {
        mediaPlayer?.setDataSource(audioUrl)
        Log.d("mesaj", "sounded")

        mediaPlayer?.prepare()
        mediaPlayer?.start()
    } catch (e: Exception) {
        Log.d("mesaj", "not sounded")

        e.printStackTrace()
    }
}

fun extractPartOfSpeech(
    jsonObject: Any,
    partOfSpeechSet: MutableSet<String>,
    synonymsSet: MutableSet<String>,
    antonymsSet: MutableSet<String>
) {
    when (jsonObject) {
        is JSONObject -> {
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val value = jsonObject[key]
                if (key == "partOfSpeech" && value is String) {
                    partOfSpeechSet.add(value)
                } else if (key == "synonyms" && value is JSONArray) {
                    for (i in 0 until value.length()) {
                        val synonym = value.getString(i)
                        synonymsSet.add(synonym)
                    }
                } else if (key == "antonyms" && value is JSONArray) {
                    for (i in 0 until value.length()) {
                        val antonym = value.getString(i)
                        antonymsSet.add(antonym)
                    }
                } else {
                    extractPartOfSpeech(value, partOfSpeechSet, synonymsSet, antonymsSet)
                }
            }
        }

        is JSONArray -> {
            for (i in 0 until jsonObject.length()) {
                extractPartOfSpeech(
                    jsonObject[i], partOfSpeechSet, synonymsSet, antonymsSet
                )
            }
        }
    }
}