package com.joker.viewModel.preView

import ambimi.rogue.core.data.LogoutReq
import ambimi.rogue.core.data.PositionReq
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.joker.BASE_URL
import com.joker.data.dto.*
import com.joker.data.remoteService.ServiceGenerator
import com.joker.data.remoteService.service.JokeService
import com.joker.data.repository.JokeGetRepository
import com.joker.ui.main.FavoriteFragment
import com.joker.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Yan
 * viewModel used for data processing
 */
@HiltViewModel
class SharedPreViewModel @Inject constructor(private val jokeGetRepository: JokeGetRepository, private val serviceGenerator: ServiceGenerator
) :
    BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val jokeInfo: MutableLiveData<JokeInfo> by lazy { MutableLiveData<JokeInfo>() }
    var jokes: LiveData<PagedList<JokeInfo>>? = null
    var words: LiveData<PagedList<Words>>? = null

    //if add a new joke, notify the joke list
    var upDatedId = MutableLiveData<String>()
    val jokeService = serviceGenerator.createService(JokeService::class.java)
    var stopQuery = false

    suspend fun getJokeFromServer(context: Context): String? {
        return jokeGetRepository.requestJoke()
    }

    suspend fun queryPosition(token: String,onfail: (code:Int)-> Unit, queryOjb:PositionReq): List<PositionData?>{
        //,PositionReq(aPosID = 275)
        val re = jokeService.checkPosition(token, queryOjb)
        Log.e("check position code ",re.code().toString() )
        if (!re.isSuccessful){
            onfail.invoke(re.code())
            return emptyList()
        }
        return re.body()?: emptyList()
    }

    suspend fun logout(token: String){
        jokeService.logout(token, LogoutReq())
    }

    suspend fun lock(token: String, position: PositionData):Boolean{
        return jokeService.lock(token, position).isSuccessful
    }

    suspend fun sendCode(token: String, position: SendCodeReq):Boolean{
        return jokeService.sendCode(token, position).isSuccessful
    }

    suspend fun verifyCode(token: String, position: VerifyCodeReq):Boolean{
        return jokeService.verifyCode(token, position).isSuccessful
    }

    suspend fun book(token: String, position: BookReq):Boolean{
        return jokeService.book(token, position).isSuccessful
    }

    suspend fun getLocalJokes(context: Context): LiveData<PagedList<JokeInfo>>? {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                jokes = jokeGetRepository.getLocalJokes(context)
            }
        }
        return jokes
    }

    suspend fun getWordsList(context: Context): LiveData<PagedList<Words>>? {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                words = jokeGetRepository.getWordsList(context)?:words
            }
        }
        return words
    }



    fun addToFavorite(context: Context) {
        jokeInfo.value?.let {
            jokeGetRepository.addToFavorite(it, context) {
                //lambda is called when inserting successful
                upDatedId.value = it.id
                Toast.makeText(context,"added to favorite list",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteJoke(joke: JokeInfo, context: Context) {
        jokeGetRepository.deleteJoke(joke,context)
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

