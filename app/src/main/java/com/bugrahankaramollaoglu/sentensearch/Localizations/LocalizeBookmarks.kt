package com.bugrahankaramollaoglu.sentensearch.Localizations

import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentBookmarksBinding


fun makeLocalizationsBookmark(languageChosen: String?,binding:FragmentBookmarksBinding) {

    when (languageChosen) {
        "de" -> {
            localizeGermanBookmark(binding)
        }

        "fr" -> {
            localizeFrenchBookmark(binding)
        }

        "tr" -> {
            localizeTurkishBookmark(binding)
        }

        "es" -> {
            localizeSpanishBookmark(binding)
        }

        "ru" -> {
            localizeRussianBookmark(binding)
        }

        "pt" -> {
            localizePortugueseBookmark(binding)
        }

        else -> {

        }
    }

}

fun localizeGermanBookmark(binding: FragmentBookmarksBinding) {

    binding.savedText.text = "Gespeicherte"
    binding.notFoundText.text = "Sespeicherter Satz\nnicht gefunden"
    binding.startSearchingText.text = "Suche starten!"

}

fun localizeFrenchBookmark(binding: FragmentBookmarksBinding) {

    binding.savedText.text = "Sauvées"
    binding.notFoundText.text = "Phrase sauvegardée\nnon trouvée"
    binding.startSearchingText.text = "Commencez à chercher!"

}

fun localizeTurkishBookmark(binding: FragmentBookmarksBinding) {

    binding.savedText.text = "Kaydedilenler"
    binding.notFoundText.text = "Henüz cümle\nkaydedilmemiş"
    binding.startSearchingText.text = "Aramaya başla!"

}

fun localizeSpanishBookmark(binding: FragmentBookmarksBinding) {

    binding.savedText.text = "Guardado"
    binding.notFoundText.text = "Sentencia guardada no encontrada"
    binding.startSearchingText.text = "Empezar a buscar!"

}

fun localizeRussianBookmark(binding: FragmentBookmarksBinding) {

    binding.savedText.text = "Сохранено"
    binding.notFoundText.text = "Cохраненное предложение\nне найдено"
    binding.startSearchingText.text = "Начните поиск!"


}

fun localizePortugueseBookmark(binding: FragmentBookmarksBinding) {

    binding.savedText.text = "Salvos"
    binding.notFoundText.text = "Frase salva não\nencontrada"
    binding.startSearchingText.text = "Comece a pesquisar!"


}
