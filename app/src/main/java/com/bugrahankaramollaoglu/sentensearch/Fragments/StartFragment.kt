package com.bugrahankaramollaoglu.sentensearch.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.bugrahankaramollaoglu.sentensearch.Localizations.makeLocalizationsStart
import com.bugrahankaramollaoglu.sentensearch.Models.SharedViewModel
import com.bugrahankaramollaoglu.sentensearch.Helpers.ThemeHelper
import com.bugrahankaramollaoglu.sentensearch.databinding.FragmentStartBinding
import com.bugrahankaramollaoglu.sentensearch.Helpers.launchFragment


class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val themeHelper: ThemeHelper = ThemeHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startSearchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.progressBar.visibility = View.VISIBLE
                // SearchView has gained focus
                startSearchViewClicked(getParentFragmentManager())
            }
        }

        makeLocalizationsStart(sharedViewModel.currentLang, binding)
        themeHelper.applyThemeStart(sharedViewModel, binding,requireContext())

    }


    private fun startSearchViewClicked(fragmentManager: FragmentManager?) {
        if (fragmentManager != null) {
            launchFragment(fragmentManager, SearchFragment())
        }
    }

}