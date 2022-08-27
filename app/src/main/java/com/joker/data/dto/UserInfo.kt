package com.joker.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserInfo(
    @Expose @SerializedName(value = "drvrLastName")
    var drvrLastName: String = "cui",

    @Expose @SerializedName(value = "keyword")
    var keyword: String = "1988",

    @Expose @SerializedName(value = "licenceNumber")
    var licenceNumber: String = "4711957"
)