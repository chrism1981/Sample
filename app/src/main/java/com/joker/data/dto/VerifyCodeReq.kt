package com.joker.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.joker.data.dto.AppointmentDt
import com.joker.data.dto.DlExam

data class VerifyCodeReq(
    @SerializedName("bookedTs")
    @Expose
    var bookedTs: String = "",

    @SerializedName("drvrID")
    @Expose
    var drvrID: Int = 1785920,

    @SerializedName("code")
    @Expose
    var code: String?
)