package com.myapplications.testconcatadapter.adapters

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.text.getSpans
import androidx.recyclerview.widget.RecyclerView
import com.myapplications.testconcatadapter.Currency
import com.myapplications.testconcatadapter.R
import java.util.regex.Matcher

class CurrencyListContentAdapter(private val currencyList: List<Currency>) :
    RecyclerView.Adapter<CurrencyListContentAdapter.DataViewHolder>(), Filterable {

    private val originalList = ArrayList(currencyList)
    var filteredCurrencies = ArrayList(currencyList)

    var constraint: String = ""

    class DataViewHolder(myItemView: View) : RecyclerView.ViewHolder(myItemView) {
        fun bind(currency: Currency, constraint: String) {
//            val sb = SpannableString(currency.name)
//            val stringStart = currency.name.lowercase().indexOf(constraint)
//            currency.name.lowercase().find {  }
//            val stringEnd = stringStart + constraint.length
//            sb.setSpan(ForegroundColorSpan(Color.RED), stringStart, stringEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//            sb.setSpan(StyleSpan(Typeface.BOLD), stringStart, stringEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            itemView.findViewById<TextView>(R.id.tvCurrencyItem).text =
                createFormattedString(currency.name, constraint)
//            tvCurrencyItem.text = currency.name
        }

        fun createFormattedString(fullText: String, constraint: String): SpannableString {
            if (constraint.isNullOrBlank()){
                return SpannableString(fullText)
            }
            val sb = SpannableString(fullText)
            val length = constraint.length
            var index = fullText.indexOf(constraint)
            val listStartsAndEnds = mutableListOf<Pair<Int, Int>>()
            Log.d("TEST SPAN STUFF", "listStartsAndEnds size: ${listStartsAndEnds.size}")
            Log.d("TEST SPAN STUFF", "listStartsAndEnds: ${listStartsAndEnds}")
            while (index >= 0) {
                Log.d("TEST SPAN STUFF", "index is: ${index}")
                val pair = Pair(index, index + length)
                listStartsAndEnds.add(pair)
                index = fullText.indexOf(constraint, index + length)
                Log.d("TEST SPAN STUFF", "listStartsAndEnds just added: ${pair}")
                Log.d("TEST SPAN STUFF", "index before next iteration is: ${index}")
            }
            Log.d("TEST SPAN STUFF", "after while loop listsize is: ${listStartsAndEnds.size}")
            if (listStartsAndEnds.isEmpty()) {
                return sb
            }
            val foregroundColor = ForegroundColorSpan(Color.RED)
            val styleSpan = StyleSpan(Typeface.BOLD)
            listStartsAndEnds.forEach { startEndPair ->
                Log.d("TEST SPAN STUFF", "listStartsAndEnds.forEach: ${startEndPair}")
                sb.setSpan(
                    ForegroundColorSpan(Color.RED),
                    startEndPair.first,
                    startEndPair.second,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                sb.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startEndPair.first,
                    startEndPair.second,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            Log.d("TEST SPAN STUFF", "returning this spannable string: ${sb}")
            return sb
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrencyListContentAdapter.DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_currency_item, parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: CurrencyListContentAdapter.DataViewHolder,
        position: Int
    ) {
//        holder.bind(currencyList[position])

        holder.bind(filteredCurrencies[position], constraint)
    }

    override fun getItemCount(): Int {
//        return currencyList.size
        return filteredCurrencies.size
    }

    override fun getFilter(): Filter {
        return MyCustomFilter(this, originalList)
    }

    fun storeConstraintText(constraint: String) {
        this.constraint = constraint
    }
}

class MyCustomFilter(
    private val adapter: CurrencyListContentAdapter,
    private val originalList: List<Currency>
) : Filter() {

    private val filteredList: MutableList<Currency> = ArrayList()

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        if (constraint == null || constraint.isEmpty()) {
            filteredList.addAll(originalList)
            adapter.storeConstraintText(constraint.toString())
            Log.d(
                "TEST",
                "Constraint is NOTHING. Added all original list. Size ${originalList.size}"
            )
        } else {
            adapter.storeConstraintText(constraint.toString())
            val filterPattern = constraint.toString().lowercase().trim()
            for (currency in originalList) {
                if (currency.name.lowercase().contains(filterPattern)) {
                    filteredList.add(currency)
                }
            }
        }
        val filterResults = FilterResults()
        filterResults.values = filteredList
        Log.d("TEST", "Filtered List Size to results size ${filteredList.size}")
        return filterResults
    }

    override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
        adapter.filteredCurrencies.clear()
        adapter.filteredCurrencies.addAll((filterResults?.values) as List<Currency>)
        adapter.notifyDataSetChanged()
    }
}