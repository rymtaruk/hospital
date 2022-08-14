package com.rymtaruk.hospital.ui.search

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rymtaruk.hospital.api.repositories.HospitalRepository
import com.rymtaruk.hospital.base.BaseViewModel
import com.rymtaruk.hospital.model.HospitalData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val hospitalRepository: HospitalRepository): BaseViewModel() {
    private val _responseHospitalData = MutableLiveData<MutableList<HospitalData>>()

    val responseHospitalData: LiveData<MutableList<HospitalData>> get() = _responseHospitalData

    init {
        loadData()
    }

    private fun loadData() {
        onDispose(
            hospitalRepository.getHospital()
                .toMaybe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _defaultLoading.value = View.VISIBLE
                }
                .doAfterTerminate {
                    _defaultLoading.value = View.GONE
                }
                .onErrorComplete(this::errorHandler)
                .subscribe{
                    _responseHospitalData.value = it
                }
        )
    }
}