package com.joker.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class lockReq(
    @SerializedName("appointmentDt")
    @Expose
    var appointmentDt: AppointmentDt = AppointmentDt(),
    @SerializedName("bookedTs")
    @Expose
    var bookedTs: String = "",
    @SerializedName("dlExam")
    @Expose
    var dlExam: DlExam = DlExam(),
    @SerializedName("drscDrvSchl")
    @Expose
    var drscDrvSchl: DrscDrvSchl = DrscDrvSchl(),
    @SerializedName("drvrDriver")
    @Expose
    var drvrDriver: DrvrDriver = DrvrDriver(),
    @SerializedName("endTm")
    @Expose
    var endTm: String = "",
    @SerializedName("instructorDlNum")
    @Expose
    var instructorDlNum: Any? = Any(),
    @SerializedName("posId")
    @Expose
    var posId: Int = 0,
    @SerializedName("resourceId")
    @Expose
    var resourceId: Int = 0,

    @SerializedName("signature")
    @Expose
    var signature: String = "",
    @SerializedName("startTm")
    @Expose
    var startTm: String = ""
) {
    class DrscDrvSchl

    data class DrvrDriver(
        @SerializedName("drvrId")
        @Expose
        var drvrId: Int = 1785920
    )
}