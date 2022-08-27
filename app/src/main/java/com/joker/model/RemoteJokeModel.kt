package com.joker.model

import com.joker.data.dto.JokeInfo
import com.joker.utils.NetworkConnectivity
import com.joker.data.remoteService.ServiceGenerator
import javax.inject.Inject
import com.joker.data.dto.UserInfo
import com.joker.data.remoteService.service.JokeService

/**
 * Created by Yan
 */

class RemoteJokeModel @Inject
constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) : BaseRemoteModel<JokeInfo>() {
    val jokeService = serviceGenerator.createService(JokeService::class.java)

    suspend fun requestJoke(): String? {
        val result = jokeService.webLogin(UserInfo())
        return result.headers()["Authorization"]
    }

}
