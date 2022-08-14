package com.rymtaruk.hospital.model

import com.google.gson.annotations.SerializedName

class AdditionalData (
    @SerializedName("jumlah_positif")
    val jumlahPositif: Long,

    @SerializedName("jumlah_meninggal")
    val jumlahMeninggal: Long,

    @SerializedName("jumlah_sembuh")
    val jumlahSembuh: Long,

    @SerializedName("jumlah_dirawat")
    val jumlahDirawat: Long,
    val tanggal: String,
    val created: String
)