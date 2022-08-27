package com.joker.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.joker.data.dto.AppointmentDt
import com.joker.data.dto.DlExam

data class BookReq(
    @SerializedName("appointment")
    @Expose
    var appointment: Appointment = Appointment(),
    @SerializedName("userId")
    @Expose
    var userId: String = "WEBD:1785920"
) {
    data class Appointment(
        @SerializedName("drvrDriver")
        @Expose
        var drvrDriver: DrvrDriver = DrvrDriver()
    ) {
        data class DrvrDriver(
            @SerializedName("drvrId")
            @Expose
            var drvrId: Int = 1785920
        )
    }
}