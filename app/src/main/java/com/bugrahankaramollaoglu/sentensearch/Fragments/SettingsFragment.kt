package com.bugrahankaramollaoglu.sentensearch.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bugrahankaramollaoglu.sentensearch.Activities.MainActivity
import com.bugrahankaramollaoglu.sentensearch.Helpers.ThemeHelper
import com.bugrahankaramollaoglu.sentensearch.Helpers.openGithubProfile
import com.bugrahankaramollaoglu.sentensearch.Helpers.openPrivacyPolicy
import com.bugrahankaramollaoglu.sentensearch.Helpers.openRateApp
import com.bugrahankaramollaoglu.sentensearch.Helpers.openTermsConditions
import com.bugrahankaramollaoglu.sentensearch.Helpers.sendFeedback
import com.bugrahankaramollaoglu.sentensearch.Helpers.vibrate
import com.bugrahankaramollaoglu.sentensearch.Localizations.engLang
import com.bugrahankaramollaoglu.sentensearch.Localizations.frenchLang
import com.bugrahankaramollaoglu.sentensearch.Localizations.germanLang
import com.bugrahankaramollaoglu.sentensearch.Localizations.makeLocalizationsSettings
import com.bugrahankaramollaoglu.sentensearch.Localizations.portugueseLang
import com.bugrahankaramollaoglu.sentensearch.Localizations.russianLang
import com.bugrahankaramollaoglu.sentensearch.Localizations.spanishLang
import com.bugrahankaramollaoglu.sentensearch.Localizations.turkishLang
import com.bugrahankaramollaoglu.sentensearch.Models.SharedViewModel
import com.bugrahankaramollaoglu.sentensearch.R
import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val themeHelper: ThemeHelper = ThemeHelper()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeLocalizationsSettings(sharedViewModel.currentLang, binding)
        themeHelper.applyThemeSettings(sharedViewModel, binding)

        val settingButtons = arrayOf(
            binding.followCard,
            binding.rateCard,
            binding.shareCard,
            binding.changeLanguageCard,
            binding.themeCard,
            binding.vibrationCard,
            binding.feedbackCard,
            binding.privacyCard,
            binding.termsCard,
        )

        settingButtons.forEach { textView ->
            textView.setOnClickListener {
                when (textView.id) {

                    R.id.followCard -> {
                        openGithubProfile(
                            "bugrahankaramollaoglu", requireContext()
                        )
                    }

                    R.id.rateCard -> {
                        openRateApp(requireContext())
                    }

                    R.id.shareCard -> {
                        shareLink("https://play.google.com/store/apps/details?id=com.bugrahankaramollaoglu.sentensearch&pcampaignid=web_share")
                    }


                    R.id.vibrationCard -> {

                        if (sharedViewModel.isDark) {
                            if (sharedViewModel.isVibration) {
                                binding.vibrateImage.setImageResource(R.drawable.non_vibration_dark)
                                sharedViewModel.isVibration = false
                            } else {
                                sharedViewModel.isVibration = true
                                binding.vibrateImage.setImageResource(R.drawable.vibration_dark)
                                vibrate(requireContext(), sharedViewModel)
                            }
                        } else {
                            if (sharedViewModel.isVibration) {
                                binding.vibrateImage.setImageResource(R.drawable.non_vibration_light)
                                sharedViewModel.isVibration = false
                            } else {
                                sharedViewModel.isVibration = true
                                binding.vibrateImage.setImageResource(R.drawable.vibration_light)
                                vibrate(requireContext(), sharedViewModel)
                            }
                        }
                    }

                    R.id.themeCard -> {

                        sharedViewModel.isDark = !sharedViewModel.isDark


                        val activity = context as MainActivity
                        activity.recreate()

                    }

                    R.id.feedbackCard -> {
                        sendFeedback(requireActivity())
                    }

                    R.id.changeLanguageCard -> {

                        val items = arrayOf(
                            engLang,
                            germanLang,
                            frenchLang,
                            turkishLang,
                            spanishLang,
                            russianLang,
                            portugueseLang
                        )

                        val builder = AlertDialog.Builder(requireContext())

                        builder.setItems(items) { _, which ->

                            when (which) {
                                0 -> {
                                    sharedViewModel.currentLang = "en"
                                    makeLocalizationsSettings("en", binding)
                                }

                                1 -> {
                                    sharedViewModel.currentLang = "de"
                                    makeLocalizationsSettings("de", binding)
                                }

                                2 -> {
                                    sharedViewModel.currentLang = "fr"
                                    makeLocalizationsSettings("fr", binding)
                                }

                                3 -> {
                                    sharedViewModel.currentLang = "tr"
                                    makeLocalizationsSettings("tr", binding)
                                }

                                4 -> {
                                    sharedViewModel.currentLang = "es"
                                    makeLocalizationsSettings("es", binding)
                                }

                                5 -> {
                                    sharedViewModel.currentLang = "ru"
                                    makeLocalizationsSettings("ru", binding)
                                }

                                6 -> {
                                    sharedViewModel.currentLang = "pt"
                                    makeLocalizationsSettings("pt", binding)
                                }
                            }
                        }.create().show()
                    }

                    R.id.privacyCard -> {
                        openPrivacyPolicy(requireContext())
                    }

                    R.id.termsCard -> {
                        openTermsConditions(requireContext())
                    }

                }
            }
        }

    }

    fun shareLink(url: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }


}