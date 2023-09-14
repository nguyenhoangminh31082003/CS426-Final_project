package com.example.cs426_final_project.fragments.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.cs426_final_project.R
import com.example.cs426_final_project.adapters.RecyclerSearchSuggestionAdapter


class SearchSuggestionFragment : Fragment() {

    var searchSuggestions = listOf("Please wait")

    private lateinit var rvSearchSuggestion: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_suggestion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvSearchSuggestion = view.findViewById(R.id.rvSearchSuggestion)

        val adapter = RecyclerSearchSuggestionAdapter(searchSuggestions)

        rvSearchSuggestion.adapter = adapter

//        val doubleBounce: Sprite = DoubleBounce()

    }

    companion object {
        fun newInstance(suggestionList: List<String>): SearchSuggestionFragment {
            return SearchSuggestionFragment().apply {
                updateSearchSuggestions(suggestionList)
            }
        }


    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateSearchSuggestions(suggestionList: List<String>) {
        searchSuggestions = suggestionList
        rvSearchSuggestion.adapter?.notifyDataSetChanged()
    }
}