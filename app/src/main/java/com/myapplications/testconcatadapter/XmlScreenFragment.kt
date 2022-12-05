package com.myapplications.testconcatadapter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapplications.testconcatadapter.adapters.CurrencyListContentAdapter
import com.myapplications.testconcatadapter.adapters.CurrencyListHeaderAdapter
import com.myapplications.testconcatadapter.databinding.FragmentXmlScreenBinding

class XmlScreenFragment : Fragment() {

    private var _binding: FragmentXmlScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var currenciesRecentListAdapter: CurrencyListContentAdapter
    private lateinit var currenciesRestListAdapter: CurrencyListContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentXmlScreenBinding.inflate(inflater, container, false)

        binding.rvCurrencies.visibility = View.GONE
        setUpRecyclerview()
        binding.rvCurrencies.visibility = View.VISIBLE

        binding.btn1.setOnClickListener {
            val x = currenciesRecentListAdapter.itemCount
            Toast.makeText(requireContext(), "recent count: $x", Toast.LENGTH_LONG).show()
        }

        binding.btn2.setOnClickListener {
            val x = currenciesRestListAdapter.itemCount
            Toast.makeText(requireContext(), "rest count: $x", Toast.LENGTH_LONG).show()
        }

        binding.btn3.setOnClickListener {
            findNavController().navigate(R.id.action_xmlScreenFragment_to_composeFragment)
        }

        binding.tilFilter.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(p0: Editable?) {
                Log.d("TEST", "after text changed: ${p0.toString()}")
                p0.let {
                    currenciesRecentListAdapter.filter.filter(p0.toString())
                    currenciesRestListAdapter.filter.filter(p0.toString())
                }
            }
        })

        return binding.root
    }

    private fun setUpRecyclerview() {
        binding.rvCurrencies.layoutManager = LinearLayoutManager(requireContext())
        val currenciesRecentHeaderAdapter = CurrencyListHeaderAdapter("Recent")
        currenciesRecentListAdapter =
            CurrencyListContentAdapter(DataSource.listRecentCurrencies)
        val currenciesRestHeaderAdapter = CurrencyListHeaderAdapter("Rest")
        currenciesRestListAdapter = CurrencyListContentAdapter(DataSource.listRestCurrencies)

        val adapter = ConcatAdapter(
            currenciesRecentHeaderAdapter,
            currenciesRecentListAdapter,
            currenciesRestHeaderAdapter,
            currenciesRestListAdapter
        )
        binding.rvCurrencies.adapter = adapter
    }

}