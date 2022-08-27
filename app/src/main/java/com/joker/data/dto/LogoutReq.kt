package ambimi.rogue.core.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LogoutReq(
    @SerializedName("appointmentDt")
    @Expose
    var appointmentDt: AppointmentDt = AppointmentDt(),
    @SerializedName("dlExam")
    @Expose
    var dlExam: DlExam = DlExam(),
    @SerializedName("drscDrvSchl")
    @Expose
    var drscDrvSchl: DrscDrvSchl = DrscDrvSchl(),
    @SerializedName("drvrDriver")
    @Expose
    var drvrDriver: DrvrDriver = DrvrDriver()
) {
    class AppointmentDt

    class DlExam

    class DrscDrvSchl

    data class DrvrDriver(
        @SerializedName("drvrId")
        @Expose
        var drvrId: Int = 1785920
    )
}