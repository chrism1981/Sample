package com.joker.model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.joker.data.dto.JokeInfo
import com.joker.data.dto.Words
import com.joker.utils.dataBase.DBHelper
import com.joker.utils.dataBase.dao.JokeInfoDao
import javax.inject.Inject

/**
 * Created by Yan
 * used for operation of database
 */

class LocalJokeModel @Inject
constructor() {
    suspend fun getLocalJokes(context: Context): LiveData<PagedList<JokeInfo>>? {
        DBHelper.getDataBase(context)?.jokeDao()?.getAllRecords()
            ?.toLiveData(Config(pageSize = 10, enablePlaceholders = true, maxSize = 30))?.let {
                return it
            }
        getWordsList(context)
        return null
    }

    val keyWords = LinkedHashMap<String, Int>()
    fun addToFavorite(joke: JokeInfo, context: Context, success: () -> Unit) {
        joke.isFavorite = true
        updateJoke(joke, context, success)
    }

    fun updateJoke(joke: JokeInfo, context: Context, success: () -> Unit) {
        val dao = DBHelper.getDataBase(context)?.jokeDao()
        dao?.queryWithKey<JokeInfo>(joke.id, {
            Toast.makeText(context, "already added", Toast.LENGTH_SHORT).show()
        }, {
            dao.insert(joke)
            success.invoke()

        })
    }

    fun deleteJoke(joke: JokeInfo, context: Context) {
        DBHelper.getDataBase(context)?.jokeDao()?.delete(joke)
    }
    private val result = MutableLiveData<List<Words>>()

    suspend fun getWordsList(context: Context): LiveData<List<Words>> {

        DBHelper.getDataBase(context)?.getDao(JokeInfoDao::class.java)?.query<JokeInfo>({
            keyWords.clear()
            it.forEach { words ->
                //这里value一直为null，这样写不可能获得值得，value只有当被observe时候才有值
                val wordsDevided = words.joke?.split(" ")
                wordsDevided?.forEach { word ->
                    if (keyWords.containsKey(word)) {
                        keyWords[word]?.let { value ->
                            keyWords[word] = value + 1
                        }
                    } else {
                        keyWords[word] = 1
                    }
                }
            }
            val wordsObjectList = ArrayList<Words>()

            keyWords.entries.sortedWith { o1, o2 -> (o2?.value ?: 0) - (o1?.value ?: 0) }
                .subList(0,(if(keyWords.size>10) 10 else keyWords.size)).forEach {entry->
                    val wordObject = Words()
                    wordObject.counts = entry.value
                    wordObject.value = entry.key
                    wordsObjectList.add(wordObject)
                }
            result.value = wordsObjectList
        },{})
        return result

        /* 这里有坑，Paging不会立刻获得值，只有当这个LIVEDATA被observe时候才会生成pagingList*/
//        source?.toLiveData(Config(pageSize = 10, enablePlaceholders = true, maxSize = 30))?.let {
//            it.value?.forEach { word ->
//                //这里value一直为null，这样写不可能获得值得，value只有当被observe时候才有值
//                val words = word.joke?.split(" ")
//                words?.forEach { word ->
//                    if (keyWords.containsKey(word)) {
//                        keyWords[word]?.let { value ->
//                            keyWords[word] = value + 1
//                        }
//                    } else {
//                        keyWords[word] = 1
//                    }
//                }
//            }
//        }


    }

}
