package com.rymtaruk.hospital.ui

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

    val responseCovidData: LiveData<CovidData> get() = _responseCovidData

    init {
        loadData()
    }

    private fun loadData() {
        onDispose(
            hospitalRepository.getCovid().toMaybe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _defaultLoading.value = true
                }
                .doAfterTerminate {
                    _defaultLoading.value = false
                }
                .onErrorComplete(this::errorHandler)
                .subscribe{
                    _responseCovidData.value = it
                }
        )
    }
}