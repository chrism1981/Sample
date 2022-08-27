package ambimi.rogue.core.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PositionReqMultiple(
    @Expose @SerializedName(value = "aPosID")
    var aPosID: Array<Int> = arrayOf(274,73),
    @Expose @SerializedName(value = "examDate")
    var examDate: String = "2022-08-30",
    @Expose @SerializedName(value = "examType")
    var examType: String = "7-R-1",
    @Expose @SerializedName(value = "ignoreReserveTime")
    var ignoreReserveTime: Boolean = false,
    @Expose @SerializedName(value = "lastName")
    var lastName: String = "CUI",
    @Expose @SerializedName(value = "licenseNumber")
    var licenseNumber: String = "4711957",
    @Expose @SerializedName(value = "prfDaysOfWeek")
    var prfDaysOfWeek: String = "[0,1,2,3,4,5,6]",
    @Expose @SerializedName(value = "prfPartsOfDay")
    var prfPartsOfDay: String = "[0,1]"
)