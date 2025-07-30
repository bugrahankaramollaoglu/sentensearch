package com.bugrahankaramollaoglu.sentensearch.Models

import android.content.Context
import android.util.JsonReader
import java.io.InputStreamReader


data class Sentence(
    val id: Int,
    val sentence: String,
    val toGerman: String,
    val toTurkish: String,
    val toFrench: String,
    val toSpanish: String,
    val toRussian: String,
    val toPortuguese: String,
    val level: String
)


class SentenceSearcher(private val context: Context) {
    private val sentences: MutableList<Sentence> = mutableListOf()

    init {
        context.assets.open("sentences_file.json").use { inputStream ->
            JsonReader(InputStreamReader(inputStream)).use { jsonReader ->
                jsonReader.beginObject()
                while (jsonReader.hasNext()) {
                    val name = jsonReader.nextName()
                    if (name == "sentences") {
                        jsonReader.beginArray()
                        while (jsonReader.hasNext()) {
                            val sentence = parseSentence(jsonReader)
                            sentences.add(sentence)
                        }
                        jsonReader.endArray()
                    } else {
                        jsonReader.skipValue()
                    }
                }
                jsonReader.endObject()
            }
        }
    }


    private fun parseSentence(jsonReader: JsonReader): Sentence {
        var id = 0
        var sentence = ""
        var toGerman = ""
        var toTurkish = ""
        var toFrench = ""
        var toSpanish = ""
        var toRussian = ""
        var toPortuguese = ""
        var level = ""

        jsonReader.beginObject()
        while (jsonReader.hasNext()) {
            when (jsonReader.nextName()) {
                "id" -> id = jsonReader.nextInt()
                "sentence" -> sentence = jsonReader.nextString()
                "toGerman" -> toGerman = jsonReader.nextString()
                "toTurkish" -> toTurkish = jsonReader.nextString()
                "toFrench" -> toFrench = jsonReader.nextString()
                "toSpanish" -> toSpanish = jsonReader.nextString()
                "toRussian" -> toRussian = jsonReader.nextString()
                "toPortuguese" -> toPortuguese = jsonReader.nextString()
                "level" -> level = jsonReader.nextString()
                else -> jsonReader.skipValue() 
            }
        }
        jsonReader.endObject()

        return Sentence(
            id,
            sentence,
            toGerman,
            toTurkish,
            toFrench,
            toSpanish,
            toRussian,
            toPortuguese,
            level
        )
    }

    fun search(query: String): List<Sentence> {
        val lowercaseQuery = query.lowercase()

        val regex = "\\b$lowercaseQuery\\b".toRegex()

        return sentences.filter { sentence ->
            sentence.sentence.lowercase().contains(regex)
        }
    }
}
