package ambimi.rogue.core.data

/**
 *Administrator
 *2022/8/26
 */
data class LoginData(
    var blockedEligibleExams: List<Any> = listOf(),
    var cancellationFeeTotal: Int = 0,
    var drvrId: Int = 0,
    var eligibleExams: List<EligibleExam> = listOf(),
    var email: String = "",
    var expandedStatus: List<ExpandedStatu> = listOf(),
    var firstName: String = "",
    var glpTrainingFlag: String = "",
    var lastName: String = "",
    var licenseNumber: String = "",
    var maxofMaxBookingDays: Int = 0,
    var optInFlags: OptInFlags = OptInFlags(),
    var phoneNum: String = "",
    var webAappointments: List<Any> = listOf(),
    var webMaxSlots: Int = 0
) {
    data class EligibleExam(
        var code: String = "",
        var description: String = "",
        var eed: Eed = Eed()
    ) {
        data class Eed(
            var date: String = "",
            var dayOfWeek: String = ""
        )
    }

    data class ExpandedStatu(
        var description: String = "",
        var master: String = "",
        var masterDesc: String = "",
        var section: String = "",
        var status: String = ""
    )

    data class OptInFlags(
        var email: String = "",
        var sms: String = ""
    )
}