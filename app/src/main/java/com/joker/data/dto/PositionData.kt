package com.joker.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *Administrator
 *2022/8/26
 */
data class PositionData(
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
    var drscDrvSchl: lockReq.DrscDrvSchl = lockReq.DrscDrvSchl(),
    @SerializedName("drvrDriver")
    @Expose
    var drvrDriver: lockReq.DrvrDriver = lockReq.DrvrDriver(),
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
    var startTm: String = "",
//    @Expose
    @SerializedName(value = "lemgMsgId")
    var lemgMsgId: Int = 0,

) {

}

data class AppointmentDt(
    @Expose @SerializedName(value = "date")
    var date: String = "",
    @Expose @SerializedName(value = "dayOfWeek")
    var dayOfWeek: String = ""
)

data class DlExam(
    @Expose @SerializedName(value = "code")
    var code: String = "",
    @Expose @SerializedName(value = "description")
    var description: String = ""
)