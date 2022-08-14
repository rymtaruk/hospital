package com.rymtaruk.hospital.api.repositories

import com.rymtaruk.hospital.api.Api
import com.rymtaruk.hospital.model.CovidData
import com.rymtaruk.hospital.model.HospitalData
import io.reactivex.Single
import javax.inject.Inject

class HospitalRepository @Inject constructor(private val _api: Api) {
    private val _hospitalUrl = "https://dekontaminasi.com/api/id/covid19/hospitals"
    private val _covidUrl = "https://data.covid19.go.id/public/api/update.json"

    fun getHospital(): Single<MutableList<HospitalData>> {
        return this._api.getHospitalData(_hospitalUrl)
    }

    fun getCovid(): Single<CovidData> {
        return this._api.getCovidData(_covidUrl)
    }
}