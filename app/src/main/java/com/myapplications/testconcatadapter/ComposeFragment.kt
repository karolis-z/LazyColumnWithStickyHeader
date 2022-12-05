package com.myapplications.testconcatadapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController


class ComposeFragment : Fragment() {

    private lateinit var composeView: ComposeView
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listRecentCurrencies = DataSource.listRecentCurrencies
        val listRestCurrencies = DataSource.listRestCurrencies
        composeView.setContent {
            ComposeScreen(searchViewModel) {
                findNavController().navigate(R.id.action_composeFragment_to_xmlScreenFragment)
            }
        }
    }

}