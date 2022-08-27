package com.joker.data.repository


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.joker.data.dto.JokeInfo
import com.joker.data.dto.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Yan
 */

interface JokeGetRepositorySource {
    suspend fun requestJoke(): String?
    suspend fun getLocalJokes(context: Context): LiveData<PagedList<JokeInfo>>?
    fun addToFavorite(joke: JokeInfo,context: Context,success: ()->Unit)
    fun updateJoke(joke:JokeInfo,context: Context,success: ()->Unit)
    fun deleteJoke(joke:JokeInfo,context: Context)
}
