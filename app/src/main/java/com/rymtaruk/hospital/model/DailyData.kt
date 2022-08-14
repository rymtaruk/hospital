package com.rymtaruk.hospital.model

import com.google.gson.annotations.SerializedName

class DailyData (
    @SerializedName("key_as_string")
    val keyAsString: String,

    val key: Long,

    @SerializedName("doc_count")
    val docCount: Long,

    @SerializedName("jumlah_meninggal")
    val jumlahMeninggal: ValueData,

    @SerializedName("jumlah_sembuh")
    val jumlahSembuh: ValueData,

    @SerializedName("jumlah_positif")
    val jumlahPositif: ValueData,

    @SerializedName("jumlah_dirawat")
    val jumlahDirawat: ValueData,

    @SerializedName("jumlah_positif_kum")
    val jumlahPositifKum: ValueData,

    @SerializedName("jumlah_sembuh_kum")
    val jumlahSembuhKum: ValueData,

    @SerializedName("jumlah_meninggal_kum")
    val jumlahMeninggalKum: ValueData,

    @SerializedName("jumlah_dirawat_kum")
    val jumlahDirawatKum: ValueData
)