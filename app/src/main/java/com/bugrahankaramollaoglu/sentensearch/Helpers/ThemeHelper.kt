package com.bugrahankaramollaoglu.sentensearch.Helpers

import android.content.Context
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bugrahankaramollaoglu.sentensearch.Models.SharedViewModel
import com.bugrahankaramollaoglu.sentensearch.R
import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentBookmarksBinding
import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentSearchBinding
import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentSettingsBinding
import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentStartBinding
import com.bugrahankaramollaoglu.sentensearch.databinding.RecyclerRowBinding

class ThemeHelper {
    fun applyThemeStart(
        sharedViewModel: SharedViewModel,
        binding: FragmentStartBinding,
        context: Context
    ) {
        if (sharedViewModel.isDark) {
            binding.startLogo.setImageResource(R.drawable.logo)
            binding.circleStart.setImageResource(R.drawable.circle)


        } else {

            binding.startLogo.setImageResource(R.drawable.logo_light)
            binding.circleStart.setImageResource(R.drawable.circle_light)

            var startSearchView = binding.startSearchView

            val searchIcon: ImageView =
                startSearchView.findViewById(androidx.appcompat.R.id.search_mag_icon)
            searchIcon.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.black
                ), PorterDuff.Mode.SRC_IN
            )

            val closeIcon: ImageView =
                startSearchView.findViewById(androidx.appcompat.R.id.search_close_btn)
            closeIcon.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.black
                ), PorterDuff.Mode.SRC_IN
            )

        }
    }

    fun applyThemeSearch(
        sharedViewModel: SharedViewModel, binding: FragmentSearchBinding, context: Context
    ) {
        if (sharedViewModel.isDark) {
            binding.resetHistory.setImageResource(R.drawable.clear_light)
            binding.shuffleButton.setImageResource(R.drawable.shuffle_button)
            binding.soundWordImage.setImageResource(R.drawable.round_volume_up_24)
            binding.goBackArrow.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.go_back_arrow,
                0
            )
        } else {
            binding.resetHistory.setImageResource(R.drawable.clear_dark)
            binding.shuffleButton.setImageResource(R.drawable.shuffle_button_light)
            binding.soundWordImage.setImageResource(R.drawable.sound_light)
            binding.goBackArrow.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.go_back_arrow_light,
                0
            )

            var searchView = binding.searchView

            val searchIcon: ImageView =
                searchView.findViewById(androidx.appcompat.R.id.search_mag_icon)
            searchIcon.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.black
                ), PorterDuff.Mode.SRC_IN
            )

            val closeIcon: ImageView =
                searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
            closeIcon.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.black
                ), PorterDuff.Mode.SRC_IN
            )

        }
    }

    fun applyThemeSettings(sharedViewModel: SharedViewModel, binding: FragmentSettingsBinding) {
        if (sharedViewModel.isDark) {
            binding.circle.setImageResource(R.drawable.circle)
            binding.feedbackImage.setImageResource(R.drawable.feedback)
            if (sharedViewModel.isVibration) {
                binding.vibrateImage.setImageResource(R.drawable.vibration_dark)
            } else {
                binding.vibrateImage.setImageResource(R.drawable.non_vibration_dark)
            }
            binding.themeImage.setImageResource(R.drawable.light_theme)
            binding.privacyImage.setImageResource(R.drawable.privacy_dark)
            binding.termsImage.setImageResource(R.drawable.terms_dark)
        } else {
            binding.circle.setImageResource(R.drawable.circle_light)
            binding.feedbackImage.setImageResource(R.drawable.feedback_light)
            if (sharedViewModel.isVibration) {
                binding.vibrateImage.setImageResource(R.drawable.vibration_light)
            } else {
                binding.vibrateImage.setImageResource(R.drawable.non_vibration_light)
            }
            binding.themeImage.setImageResource(R.drawable.theme)
            binding.privacyImage.setImageResource(R.drawable.privacy_light)
            binding.termsImage.setImageResource(R.drawable.terms_light)
        }
    }

    fun applyThemeBookmarks(sharedViewModel: SharedViewModel, binding: FragmentBookmarksBinding) {
        if (sharedViewModel.isDark) {
            binding.booksImage.setImageResource(R.drawable.books)
            binding.exportSentences.setImageResource(R.drawable.export)
            binding.circleBookmarks.setImageResource(R.drawable.circle)
        } else {
            binding.booksImage.setImageResource(R.drawable.books_light)
            binding.exportSentences.setImageResource(R.drawable.export_light)
            binding.circleBookmarks.setImageResource(R.drawable.circle_light)
        }
    }

    fun applyThemeSentenceAdapter(
        sharedViewModel: SharedViewModel,
        binding: RecyclerRowBinding,
        isBookmarked: Boolean
    ) {
        if (sharedViewModel.isDark) {
            if (isBookmarked) {
                binding.recyclerViewBookmark.setImageResource(R.drawable.bookmark_light)
            } else {
                binding.recyclerViewBookmark.setImageResource(R.drawable.unfilled_bookmark_light)
            }
        } else {
            if (isBookmarked) {
                binding.recyclerViewBookmark.setImageResource(R.drawable.baseline_bookmark_24)
            } else {
                binding.recyclerViewBookmark.setImageResource(R.drawable.outline_bookmark_border_24)
            }
        }
    }
}