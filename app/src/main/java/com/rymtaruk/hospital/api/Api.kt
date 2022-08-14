package com.rymtaruk.hospital.api

import com.rymtaruk.hospital.model.CovidData
import com.rymtaruk.hospital.model.HospitalData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface Api {
    @GET
    fun getHospitalData(@Url url: String) : Single<MutableList<HospitalData>>

    @GET
    fun getCovidData(@Url url: String) : Single<CovidData>
}