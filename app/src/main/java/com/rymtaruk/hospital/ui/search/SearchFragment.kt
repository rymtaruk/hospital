package com.rymtaruk.hospital.ui.search

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.rymtaruk.core.di.util.ViewModelFactory
import com.rymtaruk.hospital.R
import com.rymtaruk.hospital.databinding.SearchFragmentBinding

class SearchFragment : DialogFragment() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var dialogFragment: Dialog
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private var viewModelFactory: ViewModelFactory? = null
    private var _adapterSearch: SearchAdapter? = null

    private val adapterSearch: SearchAdapter
        get() {
            if (_adapterSearch == null) {
                _adapterSearch = SearchAdapter()
            }
            return _adapterSearch as SearchAdapter
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    companion object {
        fun newInstance(viewModelFactory: ViewModelFactory): SearchFragment {
            val searchFragment = SearchFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("FACTORY", viewModelFactory)
                }
            }
            return searchFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.viewModelFactory = it.getSerializable("FACTORY") as ViewModelFactory
        }
        setStyle(STYLE_NO_TITLE, R.style.Theme_Hospital);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, this.viewModelFactory!!)[SearchViewModel::class.java]
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        dialogFragment = dialog!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        onObserverData()
    }

    private fun initView() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapterSearch.filter.filter(p0!!.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.rvItems.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = adapterSearch
        }
    }

    private fun onObserverData() {
        viewModel.defaultLoading.observeForever {
            binding.pbLoading.visibility = it
        }

        viewModel.responseHospitalData.observeForever {
            adapterSearch.items = it
            adapterSearch.notifyItemRangeChanged(0, it.size - 1)
        }
    }

    override fun onResume() {
        val window = dialogFragment.window!!
        val params: WindowManager.LayoutParams = window.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        window
            .setBackgroundDrawableResource(
                android.R.color.transparent
            )
        window
            .setDimAmount(0f)
        window.attributes = params
        super.onResume()
    }
}