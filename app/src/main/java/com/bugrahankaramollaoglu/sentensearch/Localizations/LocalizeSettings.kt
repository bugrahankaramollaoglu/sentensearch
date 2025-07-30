package com.bugrahankaramollaoglu.sentensearch.Localizations

import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentSettingsBinding


fun makeLocalizationsSettings(languageChosen: String?, binding: FragmentSettingsBinding) {

    when (languageChosen) {
        "en" -> {
            localizeEnglishSettings(binding)
        }

        "de" -> {
            localizeGermanSettings(binding)
        }

        "fr" -> {
            localizeFrenchSettings(binding)
        }

        "tr" -> {
            localizeTurkishSettings(binding)
        }

        "es" -> {
            localizeSpanishSettings(binding)
        }

        "ru" -> {
            localizeRussianSettings(binding)
        }

        "pt" -> {
            localizePortugueseSettings(binding)
        }

        else -> {

        }
    }
}

fun localizeEnglishSettings(binding: FragmentSettingsBinding) {
    binding.settingsText.text = "Settings"
    binding.followTextView.text = "Follow Us  \uD83C\uDFAF"
    binding.rateTextView.text = "Rate the App  ⭐"
    binding.shareTextView.text = "Share the App  \uD83D\uDC65"
    binding.changeLanguageText.text = "Change Language  \uD83C\uDF10"

    engLang = "English"
    germanLang = "German"
    frenchLang = "French"
    turkishLang = "Turkish"
    spanishLang = "Spanish"
    russianLang = "Russian"
    portugueseLang = "Portuguese"

    engLang = "English \uD83C\uDDEC\uD83C\uDDE7"
    germanLang = "German \uD83C\uDDE9\uD83C\uDDEA"
    frenchLang = "French \uD83C\uDDEB\uD83C\uDDF7"
    turkishLang = "Turkish \uD83C\uDDF9\uD83C\uDDF7"
    spanishLang = "Spanish \uD83C\uDDEA\uD83C\uDDF8"
    russianLang = "Russian \uD83C\uDDF7\uD83C\uDDFA"
    portugueseLang = "Portuguese \uD83C\uDDF5\uD83C\uDDF9"
}

fun localizeGermanSettings(binding: FragmentSettingsBinding) {

    binding.settingsText.text = "Einstellungen"
    binding.followTextView.text = "Folgen Sie uns  \uD83C\uDFAF"
    binding.rateTextView.text = "Bewerten Sie die App  ⭐"
    binding.shareTextView.text = "Teilen Sie die App  \uD83D\uDC65"
    binding.changeLanguageText.text = "Sprache ändern  \uD83C\uDF10"


    engLang = "Englisch \uD83C\uDDEC\uD83C\uDDE7"
    germanLang = "Deutsch \uD83C\uDDE9\uD83C\uDDEA"
    frenchLang = "Französisch \uD83C\uDDEB\uD83C\uDDF7"
    turkishLang = "Türkisch \uD83C\uDDF9\uD83C\uDDF7"
    spanishLang = "Spanisch \uD83C\uDDEA\uD83C\uDDF8"
    russianLang = "Russisch \uD83C\uDDF7\uD83C\uDDFA"
    portugueseLang = "Portugiesisch \uD83C\uDDF5\uD83C\uDDF9"


}

fun localizeFrenchSettings(binding: FragmentSettingsBinding) {

    binding.settingsText.text = "Paramètres"
    binding.followTextView.text = "Suivez-nous sur  \uD83C\uDFAF"
    binding.rateTextView.text = "Noter l'application  ⭐"
    binding.shareTextView.text = "Partager l'application  \uD83D\uDC65"
    binding.changeLanguageText.text = "Changer de langue  \uD83C\uDF10"


    engLang = "Anglais \uD83C\uDDEC\uD83C\uDDE7"
    germanLang = "Allemand \uD83C\uDDE9\uD83C\uDDEA"
    frenchLang = "Français \uD83C\uDDEB\uD83C\uDDF7"
    turkishLang = "Turc \uD83C\uDDF9\uD83C\uDDF7"
    spanishLang = "Espagnol \uD83C\uDDEA\uD83C\uDDF8"
    russianLang = "Russe \uD83C\uDDF7\uD83C\uDDFA"
    portugueseLang = "Portugais \uD83C\uDDF5\uD83C\uDDF9"


}

fun localizeTurkishSettings(binding: FragmentSettingsBinding) {

    binding.settingsText.text = "Ayarlar"
    binding.followTextView.text = "Bizi Takip Edin  \uD83C\uDFAF"
    binding.rateTextView.text = "Uygulamaya Oy Verin  ⭐"
    binding.shareTextView.text = "Uygulamayı Paylaşın  \uD83D\uDC65"
    binding.changeLanguageText.text = "Uygulama Dilini Değiştirin  \uD83C\uDF10"


    engLang = "İngilizce \uD83C\uDDEC\uD83C\uDDE7"
    germanLang = "Almanca \uD83C\uDDE9\uD83C\uDDEA"
    frenchLang = "Fransızca \uD83C\uDDEB\uD83C\uDDF7"
    turkishLang = "Türkçe \uD83C\uDDF9\uD83C\uDDF7"
    spanishLang = "İspanyolca \uD83C\uDDEA\uD83C\uDDF8"
    russianLang = "Rusça \uD83C\uDDF7\uD83C\uDDFA"
    portugueseLang = "Portekizce \uD83C\uDDF5\uD83C\uDDF9"

}

fun localizeSpanishSettings(binding: FragmentSettingsBinding) {

    binding.settingsText.text = "Configuración"
    binding.followTextView.text = "Síguenos \uD83C\uDFAF"
    binding.rateTextView.text = "Valora la aplicación  ⭐"
    binding.shareTextView.text = "Compartir la aplicación  \uD83D\uDC65"
    binding.changeLanguageText.text = "Cambiar idioma  \uD83C\uDF10"




    engLang = "Inglés \uD83C\uDDEC\uD83C\uDDE7"
    germanLang = "Alemán \uD83C\uDDE9\uD83C\uDDEA"
    frenchLang = "Francés \uD83C\uDDEB\uD83C\uDDF7"
    turkishLang = "Turco \uD83C\uDDF9\uD83C\uDDF7"
    spanishLang = "Español \uD83C\uDDEA\uD83C\uDDF8"
    russianLang = "Ruso \uD83C\uDDF7\uD83C\uDDFA"
    portugueseLang = "Portugués \uD83C\uDDF5\uD83C\uDDF9"


}

fun localizeRussianSettings(binding: FragmentSettingsBinding) {

    binding.settingsText.text = "Настройки"
    binding.followTextView.text = "Подписаться на социальные сети \uD83C\uDFAF"
    binding.rateTextView.text = "Оценить приложение  ⭐"
    binding.shareTextView.text = "Поделиться приложением  \uD83D\uDC65"
    binding.changeLanguageText.text = "Изменить язык  \uD83C\uDF10"


    engLang = "Английский \uD83C\uDDEC\uD83C\uDDE7"
    germanLang = "Немецкий \uD83C\uDDE9\uD83C\uDDEA"
    frenchLang = "Французский \uD83C\uDDEB\uD83C\uDDF7"
    turkishLang = "Турецкий \uD83C\uDDF9\uD83C\uDDF7"
    spanishLang = "Испанский \uD83C\uDDEA\uD83C\uDDF8"
    russianLang = "Русский \uD83C\uDDF7\uD83C\uDDFA"
    portugueseLang = "Португальский \uD83C\uDDF5\uD83C\uDDF9"


}

fun localizePortugueseSettings(binding: FragmentSettingsBinding) {

    binding.settingsText.text = "Configurações"
    binding.followTextView.text = "Siga-nos  \uD83C\uDFAF"
    binding.rateTextView.text = "Avalie o aplicativo  ⭐"
    binding.shareTextView.text = "Compartilhar o aplicativo  \uD83D\uDC65"
    binding.changeLanguageText.text = "Alterar o idioma  \uD83C\uDF10"


    engLang = "Inglês"
    germanLang = "Alemão"
    frenchLang = "francês"
    turkishLang = "Turco"
    spanishLang = "Espanhol"
    russianLang = "Russo"
    portugueseLang = "português"


    engLang = "Inglês \uD83C\uDDEC\uD83C\uDDE7"
    germanLang = "Alemão \uD83C\uDDE9\uD83C\uDDEA"
    frenchLang = "francês \uD83C\uDDEB\uD83C\uDDF7"
    turkishLang = "Turco \uD83C\uDDF9\uD83C\uDDF7"
    spanishLang = "Espanhol \uD83C\uDDEA\uD83C\uDDF8"
    russianLang = "Russo \uD83C\uDDF7\uD83C\uDDFA"
    portugueseLang = "português \uD83C\uDDF5\uD83C\uDDF9"

}