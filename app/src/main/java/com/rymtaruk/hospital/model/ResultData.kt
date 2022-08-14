package com.rymtaruk.hospital.model

import com.google.gson.annotations.SerializedName

class ResultData (
    val id: Long,

    @SerializedName("jumlah_odp")
    val jumlahOdp: Long,

    @SerializedName("jumlah_pdp")
    val jumlahPDP: Long,

    @SerializedName("total_spesimen")
    val totalSpesimen: Long,

    @SerializedName("total_spesimen_negatif")
    val totalSpesimenNegatif: Long
)