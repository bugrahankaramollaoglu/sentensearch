package com.bugrahankaramollaoglu.sentensearch.Helpers

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.JsonReader
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bugrahankaramollaoglu.sentensearch.Adapters.SentencesAdapter
import com.bugrahankaramollaoglu.sentensearch.HistoryAdapter
import com.bugrahankaramollaoglu.sentensearch.Models.Sentence
import com.bugrahankaramollaoglu.sentensearch.Models.SharedViewModel
import com.bugrahankaramollaoglu.sentensearch.R
import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentSearchBinding
import java.io.IOException
import java.io.InputStreamReader

fun launchFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    addToBackStack: Boolean = true
) {
    val transaction = fragmentManager.beginTransaction()
        .replace(R.id.container, fragment)

    if (addToBackStack) {
        transaction.addToBackStack(null)
    }

    transaction.commit()
}

fun openGithubProfile(username: String, context: Context) {
    val githubURI = Uri.parse("http://github.com/$username")
    val githubIntent = Intent(Intent.ACTION_VIEW, githubURI)
    context.startActivity(githubIntent)
}

fun openPrivacyPolicy(context: Context) {
    val url =
        Uri.parse("https://www.freeprivacypolicy.com/live/6b766a10-2c2c-41aa-b3ab-f51ae2dcd128")
    val intent = Intent(Intent.ACTION_VIEW, url)
    context.startActivity(intent)
}

fun openTermsConditions(context: Context) {
    val url =
        Uri.parse("https://www.privacypolicyonline.com/live.php?token=iRsI7QZPCiWNT3KDIay6mWqWJwZAmfyd")
    val intent = Intent(Intent.ACTION_VIEW, url)
    context.startActivity(intent)
}

fun openRateApp(context:Context) {
    val url =
        Uri.parse("https://play.google.com/store/apps/details?id=com.bugrahankaramollaoglu.sentensearch")
    val intent = Intent(Intent.ACTION_VIEW, url)
    context.startActivity(intent)
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

fun isQueryValid(query: String?): Boolean {
    return query?.let {
        it.length > 1 && Regex("^[a-zA-Z ]+$").matches(it)
    } ?: false
}

fun calculateTotalResults(
    totalLanguage: String?,
    filteredSentences: List<Sentence>,
): CharSequence? {

    val totalNumber: String = filteredSentences.size.toString()
    val totalNumberFormatted = formatTotalNumber(totalNumber)

    val totalResultsString = when (totalLanguage) {
        "de" -> "Gesamtergebnisse: $totalNumberFormatted"
        "fr" -> "Résultats Totaux: $totalNumberFormatted"
        "tr" -> "Toplam Sonuç: $totalNumberFormatted"
        "es" -> "Resultados Totales: $totalNumberFormatted"
        "ru" -> "общие результаты: $totalNumberFormatted"
        "pt" -> "Resultados Totais: $totalNumberFormatted"
        else -> "Total Results: $totalNumberFormatted"
    }


    return totalResultsString
}


fun formatTotalNumber(input: String): String {
    val number = input.toDoubleOrNull()

    return if (number != null && number > 1000) {
        val formattedNumber = String.format("%.3f", number / 1000.0)
        formattedNumber.replace(",", ".")
    } else {
        input
    }
}

fun automaticallyOpenKeyboard(binding: FragmentSearchBinding, context: Context, view: View) {
    binding.searchView.requestFocus()

    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    view.postDelayed({
        imm.showSoftInput(view.findFocus(), InputMethodManager.SHOW_IMPLICIT)
    }, 200)
}

fun readJsonDataFromAssets(fileName: String, context: Context): String {
    val jsonStringBuilder = StringBuilder()
    try {
        val inputStream = context.assets.open(fileName)
        val jsonReader = JsonReader(InputStreamReader(inputStream))
        jsonReader.use {
            jsonReader.beginObject()
            while (jsonReader.hasNext()) {
                val name = jsonReader.nextName()
                if (name == "your_array_key") {
                    jsonReader.beginArray()
                    while (jsonReader.hasNext()) {
                        jsonStringBuilder.append(jsonReader.nextString())
                    }
                    jsonReader.endArray()
                } else {
                    jsonReader.skipValue()
                }
            }
            jsonReader.endObject()
        }
        inputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return jsonStringBuilder.toString()
}

fun sendFeedback(context: Context) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("DİKKAT")
    builder.setMessage("Bize geri bildirimde bulunmak istediğinizden emin misiniz?")
    builder.setPositiveButton("Evet") { dialog, _ ->
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:sentensearch@gmail.com")
        emailIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            "SentenSearch Geri Bildirim"
        )
        emailIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Dilek, öneri ya da şikayetiniz nedir?\n\n"
        )

        try {
            context.startActivity(emailIntent)
        } catch (e: Exception) {
            Log.e("mesaj", e.toString())
        }
        dialog.dismiss()
    }
    builder.setNegativeButton("Hayır") { dialog, _ ->
        dialog.dismiss()
    }
    val alertDialog = builder.create()
    alertDialog.show()
}


fun vibrate(context: Context, sharedViewModel: SharedViewModel) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?

    if (sharedViewModel.isVibration) {
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        30, VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val effect = VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE)
                    vibrator.vibrate(effect)
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(30)
                }
            }
        }
    }
}

fun closeButtonAction(
    binding: FragmentSearchBinding,
    flipHelper: FlipHelper,
    view: View,
    context: Context,
    sentencesAdapter: SentencesAdapter
) {
    hideAll(binding)
    binding.historyRecyclerView.visibility = View.VISIBLE
    binding.searchView.setQuery("", false)
    automaticallyOpenKeyboard(binding, context, view)
    if (!flipHelper.isFront) {
        flipHelper.flipCards(binding)
    }
    sentencesAdapter.resetSelectedItemPosition()
    binding.resetHistory.visibility = View.VISIBLE
}

fun shuffleButtonAction(
    context: Context,
    sentencesAdapter: SentencesAdapter,
    binding: FragmentSearchBinding,
    filteredSentences: List<Sentence>
) {
    sentencesAdapter.resetSelectedItemPosition()
    binding.sentencesRecyclerView.smoothScrollToPosition(0)
    shuffleSentences(filteredSentences, sentencesAdapter)
    val fadeAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
    binding.sentencesRecyclerView.startAnimation(fadeAnimation)
}


fun shuffleSentences(filteredSentences: List<Sentence>, sentencesAdapter: SentencesAdapter) {
    if (filteredSentences.size > 10) {
        val shuffledList = filteredSentences.shuffled()
        val randomList = shuffledList.subList(0, 10)
        sentencesAdapter.getDisplayedSentences(randomList)
    } else {
        val shuffledList = filteredSentences.shuffled()
        sentencesAdapter.getDisplayedSentences(shuffledList)
    }
    sentencesAdapter.notifyDataSetChanged()
}

fun clearHistory(
    sharedViewModel: SharedViewModel,
    context: Context,
    historyAdapter: HistoryAdapter
) {
    val historyList = sharedViewModel.history
    if (historyList != null && historyList.isNotEmpty()) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("DİKKAT")
        builder.setMessage("Arama geçmişini silmek istiyor musunuz?")
        builder.setPositiveButton("Evet") { dialog, which ->
            // Clear the history list
            historyList.clear()

            // Update the history list in SharedPreferences
            sharedViewModel.history = historyList

            // Notify the adapter of the data set change
            historyAdapter.notifyDataSetChanged()
        }.setNegativeButton("Hayır") { dialog, which ->
            // Do nothing if the user chooses not to clear the history
        }.create().show()
    }
}

fun exportSentences(sharedViewModel: SharedViewModel, context: Context) {
    val formattedExportText = buildString {
        sharedViewModel.bookmarkedSentences.forEachIndexed { index, sentence ->
            append("${index + 1}. ${sentence.sentence}\n")
        }
    }

    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.putExtra(
        Intent.EXTRA_TEXT,
        formattedExportText.trim()
    )
    intent.type = "text/plain"
    context.startActivity(Intent.createChooser(intent, "Share To:"))
}
