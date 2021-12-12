package com.joker.viewModel.preView

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.joker.BASE_URL
import com.joker.data.dto.JokeInfo
import com.joker.data.dto.Words
import com.joker.data.repository.JokeGetRepository
import com.joker.utils.dataBase.DBHelper
import com.joker.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Yan
 * viewModel used for data processing
 */
@HiltViewModel
class SharedPreViewModel @Inject constructor(private val jokeGetRepository: JokeGetRepository) :
    BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val jokeInfo: MutableLiveData<JokeInfo> by lazy { MutableLiveData<JokeInfo>() }
    var jokes: LiveData<PagedList<JokeInfo>>? = null
    var words: LiveData<List<Words>>? = null

    //if add a new joke, notify the joke list
    var upDatedId = MutableLiveData<String>()

    fun getJokeFromServer(context: Context) {
        viewModelScope.launch {
            jokeGetRepository.requestJoke().collect {
                it.data?.joke?.apply {
                    val dao = DBHelper.getDataBase(context)?.jokeDao()
                    dao?.queryWithKey<JokeInfo>(it.data.id, {
                        //if database contains this joke, fetch again
                        getJokeFromServer(context)
                    }, {
                        jokeInfo.value = it.data
                    })
                }
            }
        }
    }

    suspend fun getLocalJokes(context: Context): LiveData<PagedList<JokeInfo>>? {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                jokes = jokeGetRepository.getLocalJokes(context)
            }
        }
        return jokes
    }

    suspend fun getWordsList(context: Context): LiveData<List<Words>>? {
        words = jokeGetRepository.getWordsList(context)
        return words
    }



    fun addToFavorite(context: Context) {
        jokeInfo.value?.let {
            viewModelScope.launch {
                jokeGetRepository.addToFavorite(it, context) {
                    //lambda is called when inserting successful
                    upDatedId.value = it.id
                    Toast.makeText(context,"added to favorite list",Toast.LENGTH_SHORT).show()
                }
                jokeGetRepository.getWordsList(context)
            }

        }
    }

    fun deleteJoke(joke: JokeInfo, context: Context) {
        viewModelScope.launch {
            jokeGetRepository.deleteJoke(joke,context)
            jokeGetRepository.getWordsList(context)
        }
    }

    fun shareJoke(activity:Activity,joke: JokeInfo? = jokeInfo.value){
        val content = ShareLinkContent.Builder()
            .setContentUrl(Uri.parse(BASE_URL))
            .setQuote(joke?.joke)
            .build()
        val shareDialog = ShareDialog(activity)
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC)
    }

}

