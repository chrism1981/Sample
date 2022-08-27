package com.joker.data.remoteService.service

import ambimi.rogue.core.data.*
import com.joker.data.dto.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Yan
 */

interface JokeService {
    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String?): Call<ResponseBody?>?

//    @Headers("Accept: application/json")
//    @GET(".")
//    suspend fun getJokeInfo():Response<JokeInfo>?


    @Headers("Expires: 0",
        "Accept: application/json, text/plain, */*",
        "Cache-control: no-cache, no-store",
        "Content-Type: application/json",
        "Referer: https://onlinebusiness.icbc.com/webdeas-ui/login;type=driver",
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36"
    )
    @PUT("webLogin/webLogin")
    suspend fun webLogin(
        @Body user: UserInfo
    ): Response<LoginData>


    @Headers("Expires: 0",
        "Accept: application/json, text/plain, */*",
        "Cache-control: no-cache, no-store",
        "Content-Type: application/json",
        "Referer: https://onlinebusiness.icbc.com/webdeas-ui/booking",
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36"
    )
    @POST("web/getAvailableAppointments")
    suspend fun checkPosition(
        @Header("Authorization") token: String,
        @Body user: PositionReq
    ): Response<List<PositionData?>>

//    @Headers("Expires: 0",
//        "Accept: application/json, text/plain, */*",
//        "Cache-control: no-cache, no-store",
//        "Content-Type: application/json",
//        "Referer: https://onlinebusiness.icbc.com/webdeas-ui/booking",
//        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36"
//    )
//    @POST("web/getAvailableSlots")
//    suspend fun checkPosition2(
//        @Header("Authorization") token: String,
//        @Body user: PositionReqMultiple
//    ): Response<List<PositionData>>
//

    @Headers("Expires: 0",
        "Accept: application/json, text/plain, */*",
        "Cache-control: no-cache, no-store",
        "Content-Type: application/json",
        "Referer: https://onlinebusiness.icbc.com/webdeas-ui/booking",
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36"
    )
    @PUT("web/lock")
    suspend fun lock(
        @Header("Authorization") token: String,
        @Body user: PositionData
    ): Response<LockResponse>


    @Headers("Expires: 0",
        "Accept: application/json, text/plain, */*",
        "Cache-control: no-cache, no-store",
        "Content-Type: application/json",
        "Referer: https://onlinebusiness.icbc.com/webdeas-ui/booking",
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36"
    )
    @POST("web/sendOTP")
    suspend fun sendCode(
        @Header("Authorization") token: String,
        @Body user: SendCodeReq
    ): Response<LockResponse>


    @Headers("Expires: 0",
        "Accept: application/json, text/plain, */*",
        "Cache-control: no-cache, no-store",
        "Content-Type: application/json",
        "Referer: https://onlinebusiness.icbc.com/webdeas-ui/booking",
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36"
    )
    @PUT("web/verifyOTP")
    suspend fun verifyCode(
        @Header("Authorization") token: String,
        @Body user: VerifyCodeReq
    ): Response<Void>


    @Headers("Expires: 0",
        "Accept: application/json, text/plain, */*",
        "Cache-control: no-cache, no-store",
        "Content-Type: application/json",
        "Referer: https://onlinebusiness.icbc.com/webdeas-ui/booking",
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36"
    )
    @PUT("web/book")
    suspend fun book(
        @Header("Authorization") token: String,
        @Body user: BookReq
    ): Response<Void>


    @Headers("Expires: 0",
        "Accept: application/json, text/plain, */*",
        "Cache-control: no-cache, no-store",
        "Content-Type: application/json",
        "Referer: https://onlinebusiness.icbc.com/webdeas-ui/home",
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36"
    )
    @PUT("web/lock")
    suspend fun logout(
        @Header("Authorization") token: String,
        @Body user: LogoutReq
    ): Response<Void>


}
