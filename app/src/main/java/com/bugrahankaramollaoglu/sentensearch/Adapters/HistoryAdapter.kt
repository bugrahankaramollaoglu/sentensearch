package com.bugrahankaramollaoglu.sentensearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bugrahankaramollaoglu.sentensearch.Models.SharedViewModel

class HistoryAdapter(
    private val sharedViewModel: SharedViewModel,
    private val onItemClick: (String) -> Unit
) :
    RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {

    class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.historyRowTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_row, parent, false)
        return HistoryHolder(view)
    }

    override fun getItemCount(): Int {
        return sharedViewModel.history?.size ?: 0

    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val historyItem = sharedViewModel.history!![position]
        holder.textView.text = historyItem

        val historyIcon = holder.itemView.findViewById<ImageView>(R.id.history_icon)
        if (sharedViewModel.isDark) {
            historyIcon.setImageResource(R.drawable.history_icon)
        } else {
            historyIcon.setImageResource(R.drawable.history_icon_light)
        }

        holder.textView.setOnClickListener {
            onItemClick(historyItem)
        }

    }

}

