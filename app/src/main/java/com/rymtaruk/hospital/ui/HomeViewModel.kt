package com.rymtaruk.hospital.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rymtaruk.hospital.api.repositories.HospitalRepository
import com.rymtaruk.hospital.base.BaseViewModel
import com.rymtaruk.hospital.model.CovidData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val hospitalRepository: HospitalRepository) : BaseViewModel() {
    private val _responseCovidData = MutableLiveData<CovidData>()
    private val _showContent = MutableLiveData<Int>()

    val responseCovidData: LiveData<CovidData> get() = _responseCovidData
    val showContent: LiveData<Int> get() = _showContent

    init {
        loadData()
    }

    private fun loadData() {
        onDispose(
            hospitalRepository.getCovid().toMaybe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingState.value = View.VISIBLE
                    _showContent.value = View.GONE
                }
                .doAfterTerminate {
                    loadingState.value = View.GONE
                    _showContent.value = View.VISIBLE
                }
                .onErrorComplete(this::errorHandler)
                .subscribe(_responseCovidData::setValue)
        )
    }
}