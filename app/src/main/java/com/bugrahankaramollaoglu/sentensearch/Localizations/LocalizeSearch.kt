package com.bugrahankaramollaoglu.sentensearch.Localizations

import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentSearchBinding

fun makeLocalizationsSearch(currentLang: String?, binding: FragmentSearchBinding) {
    when (currentLang) {
        "de" -> {
            localizeGermanSearch(binding)
        }

        "fr" -> {
            localizeFrenchSearch(binding)
        }

        "tr" -> {
            localizeTurkishSearch(binding)
        }

        "es" -> {
            localizeSpanishSearch(binding)
        }

        "ru" -> {
            localizeRussianSearch(binding)
        }

        "pt" -> {
            localizePortugueseSearch(binding)
        }

        else -> {

        }
    }
}


fun localizeGermanSearch(binding: FragmentSearchBinding) {

    binding.wordDetailsTextView.text = "Definition"
    binding.wordSentencesTextView.text = "Sätze"
    binding.nounText.text = "Substantiv"
    binding.verbText.text = "Verb"
    binding.adjectiveText.text = "Adjektiv"
    binding.adverbText.text = "Adverbe"
    binding.flipCardText.text = "Die Karte umdrehen"


}

fun localizeFrenchSearch(binding: FragmentSearchBinding) {

    binding.wordDetailsTextView.text = "Définition"
    binding.wordSentencesTextView.text = "Phrases"
    binding.nounText.text = "Nom"
    binding.verbText.text = "fiil"
    binding.adjectiveText.text = "Adjectif"
    binding.adverbText.text = "Adverbe"
    binding.flipCardText.text = "Retourner la carte"

}

fun localizeTurkishSearch(binding: FragmentSearchBinding) {

    binding.wordDetailsTextView.text = "Tanım"
    binding.wordSentencesTextView.text = "Cümleler"
    binding.nounText.text = "isim"
    binding.verbText.text = "fiil"
    binding.adjectiveText.text = "sıfat"
    binding.flipCardText.text = "Kartı çevirin"
    binding.adverbText.text = "zarf"

}

fun localizeSpanishSearch(binding: FragmentSearchBinding) {

    binding.wordDetailsTextView.text = "Definición"
    binding.wordSentencesTextView.text = "Frases"
    binding.nounText.text = "sustantivo"
    binding.verbText.text = "verbo"
    binding.flipCardText.text = "Vuelta a la Tarjeta"
    binding.adjectiveText.text = "adjetivo"
    binding.adverbText.text = "adverbio"

}

fun localizeRussianSearch(binding: FragmentSearchBinding) {

    binding.wordDetailsTextView.text = "Определение"
    binding.wordSentencesTextView.text = "Предложения"
    binding.nounText.text = "сущ"
    binding.verbText.text = "Глагол"
    binding.adjectiveText.text = "Прилагательное"
    binding.flipCardText.text = "переверните карту"
    binding.adverbText.text = "Наречие"

}

fun localizePortugueseSearch(binding: FragmentSearchBinding) {

    binding.wordDetailsTextView.text = "Definições"
    binding.wordSentencesTextView.text = "Sentenças"
    binding.nounText.text = "substantivo"
    binding.flipCardText.text = "Virar o cartão"
    binding.verbText.text = "verbo"
    binding.adjectiveText.text = "adjetivo"
    binding.adverbText.text = "advérbio"

}