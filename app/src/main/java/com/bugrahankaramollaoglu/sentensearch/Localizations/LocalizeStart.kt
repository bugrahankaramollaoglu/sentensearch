package com.bugrahankaramollaoglu.sentensearch.Localizations

import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentStartBinding


fun makeLocalizationsStart(currentLang: String?, binding: FragmentStartBinding) {

    when (currentLang) {
        "de" -> {
            localizeGermanStart(binding)
        }

        "fr" -> {
            localizeFrenchStart(binding)
        }

        "tr" -> {
            localizeTurkishStart(binding)
        }

        "es" -> {
            localizeSpanishStart(binding)
        }

        "ru" -> {
            localizeRussianStart(binding)
        }

        "pt" -> {
            localizePortugueseStart(binding)
        }

        else -> {

        }
    }
}

fun localizeGermanStart(binding: FragmentStartBinding) {

    binding.textView.text = "Lernen Sie Englisch mit Beispielsätzen!"
    binding.startSearchView.queryHint = "Ein Wort suchen..."

}

fun localizeFrenchStart(binding: FragmentStartBinding) {

    binding.textView.text = "Apprenez l'anglais à l'aide d'exemples de phrases !"
    binding.startSearchView.queryHint = "Cherchez un mot..."

}

fun localizeTurkishStart(binding: FragmentStartBinding) {

    binding.textView.text = "Örnek cümlelerle İngilizce öğren!"
    binding.startSearchView.queryHint = "Kelime aramaya başla..."

}

fun localizeSpanishStart(binding: FragmentStartBinding) {

    binding.textView.text = "Aprenda inglés con frases de ejemplo!"
    binding.startSearchView.queryHint = "Busca una palabra..."

}

fun localizeRussianStart(binding: FragmentStartBinding) {
    binding.textView.text = "Изучайте английский на реальных примерах!"
    binding.startSearchView.queryHint = "Найдите слово..."
}

fun localizePortugueseStart(binding: FragmentStartBinding) {
    binding.textView.text = "Aprenda inglês com frases de exemplo!"
    binding.startSearchView.queryHint = "Procure uma palavra..."
}
