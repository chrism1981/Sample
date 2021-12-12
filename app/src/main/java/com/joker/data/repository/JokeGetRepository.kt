package com.joker.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.joker.data.dto.JokeInfo
import com.joker.data.dto.Resource
import com.joker.data.dto.Words
import com.joker.model.LocalJokeModel
import com.joker.model.RemoteJokeModel
import com.joker.utils.ioThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by Yan
 * repository for both remote and local data.
 */

class JokeGetRepository @Inject constructor(
    private val remoteJokeModel: RemoteJokeModel,
    private val localJokeModel: LocalJokeModel,
    private val ioDispatcher: CoroutineContext
) :
    JokeGetRepositorySource {

    override suspend fun requestJoke(): Flow<Resource<JokeInfo>> {
        return flow { emit(remoteJokeModel.requestJoke()) }.flowOn(ioDispatcher)
    }

    override suspend fun getLocalJokes(context: Context): LiveData<PagedList<JokeInfo>>? {
        return localJokeModel.getLocalJokes(context)
    }

    override  fun addToFavorite(joke: JokeInfo,context: Context,success: ()->Unit) = ioThread {
        localJokeModel.addToFavorite(joke,context,success)
    }

    override  fun updateJoke(joke: JokeInfo, context: Context,success:()->Unit) = ioThread {
        localJokeModel.updateJoke(joke,context,success)
    }

    override fun deleteJoke(joke: JokeInfo, context: Context) = ioThread{
        localJokeModel.deleteJoke(joke,context)
    }

    override suspend fun getWordsList(context: Context): LiveData<List<Words>>? {
        return localJokeModel.getWordsList(context)
    }


}
