package com.example.cs426_final_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cs426_final_project.R
import com.example.cs426_final_project.fragments.search.SearchSuggestionFragment

class RecyclerSearchSuggestionAdapter(
    private val searchSuggestions: List<String>,
        private val onItemClickListener: SearchSuggestionFragment.Companion.OnItemClickListener?
) : RecyclerView.Adapter<RecyclerSearchSuggestionAdapter.ViewHolder>() {

        class ViewHolder(
            itemView: View

        ) : RecyclerView.ViewHolder(itemView) {
                val tvSearchSuggestion = itemView.findViewById<TextView>(R.id.tvSearchSuggestion)!!
                val llSearchSuggestionItem = itemView.findViewById<View>(R.id.llSearchSuggestionItem)!!
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_suggestion, parent, false)

                return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val item = searchSuggestions[position]
                holder.tvSearchSuggestion.text = item
                holder.llSearchSuggestionItem.setOnClickListener {
                        onItemClickListener?.onItemClick(it, position, item)
                }
        }

        override fun getItemCount(): Int {
                return searchSuggestions.size
        }
}