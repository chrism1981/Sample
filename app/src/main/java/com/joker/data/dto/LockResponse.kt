package com.joker.data.dto
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


/**
 *Administrator
 *2022/8/26
 */
data class LockResponse(
    @SerializedName("appointmentDt")
    @Expose
    var appointmentDt: AppointmentDt = AppointmentDt(),
    @SerializedName("bookedIndicator")
    @Expose
    var bookedIndicator: String = "",
    @SerializedName("bookedTs")
    @Expose
    var bookedTs: String = "",
    @SerializedName("checkTm")
    @Expose
    var checkTm: String = "",
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
    @SerializedName("lemgMsgId")
    @Expose
    var lemgMsgId: Int = 0,
    @SerializedName("officeNum")
    @Expose
    var officeNum: Int = 0,
    @SerializedName("posGeo")
    @Expose
    var posGeo: PosGeo = PosGeo(),
    @SerializedName("posId")
    @Expose
    var posId: Int = 0,
    @SerializedName("posName")
    @Expose
    var posName: String = "",
    @SerializedName("resourceId")
    @Expose
    var resourceId: Int = 0,
    @SerializedName("startTm")
    @Expose
    var startTm: String = "",
    @SerializedName("statusCode")
    @Expose
    var statusCode: String = ""
) {
    data class AppointmentDt(
        @SerializedName("date")
        @Expose
        var date: String = "",
        @SerializedName("dayOfWeek")
        @Expose
        var dayOfWeek: String = ""
    )

    data class DlExam(
        @SerializedName("code")
        @Expose
        var code: String = "",
        @SerializedName("description")
        @Expose
        var description: String = ""
    )

    class DrscDrvSchl

    data class DrvrDriver(
        @SerializedName("drvrId")
        @Expose
        var drvrId: Int = 0,
        @SerializedName("email")
        @Expose
        var email: String = "",
        @SerializedName("firstName")
        @Expose
        var firstName: String = "",
        @SerializedName("lastName")
        @Expose
        var lastName: String = "",
        @SerializedName("licenseNumber")
        @Expose
        var licenseNumber: String = "",
        @SerializedName("optInFlags")
        @Expose
        var optInFlags: OptInFlags = OptInFlags(),
        @SerializedName("phoneNum")
        @Expose
        var phoneNum: String = ""
    ) {
        data class OptInFlags(
            @SerializedName("email")
            @Expose
            var email: String = "",
            @SerializedName("sms")
            @Expose
            var sms: String = ""
        )
    }

    data class PosGeo(
        @SerializedName("address")
        @Expose
        var address: String = "",
        @SerializedName("address1")
        @Expose
        var address1: String = "",
        @SerializedName("agency")
        @Expose
        var agency: String = "",
        @SerializedName("city")
        @Expose
        var city: String = "",
        @SerializedName("lat")
        @Expose
        var lat: Double = 0.0,
        @SerializedName("lng")
        @Expose
        var lng: Double = 0.0,
        @SerializedName("posId")
        @Expose
        var posId: Int = 0,
        @SerializedName("postcode")
        @Expose
        var postcode: String = "",
        @SerializedName("province")
        @Expose
        var province: String = "",
        @SerializedName("url")
        @Expose
        var url: String = ""
    )
}