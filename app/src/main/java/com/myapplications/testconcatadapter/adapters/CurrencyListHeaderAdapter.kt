package com.myapplications.testconcatadapter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapplications.testconcatadapter.R

class CurrencyListHeaderAdapter(private val header: String) : RecyclerView.Adapter<CurrencyListHeaderAdapter.DataViewHolder>() {

    class DataViewHolder(val myItemView: View) : RecyclerView.ViewHolder(myItemView) {

        fun bind(header: String) {
            myItemView.findViewById<TextView>(R.id.tvCurrencyListHeader).text = header
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_currency_header, parent, false)
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(header)
    }

    override fun getItemCount(): Int = 1
}