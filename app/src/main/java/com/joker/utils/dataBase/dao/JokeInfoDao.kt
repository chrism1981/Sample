package com.joker.utils.dataBase.dao


import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.joker.data.dto.JokeInfo
import com.joker.utils.dataBase.annotations.IsQuery
import com.joker.utils.dataBase.annotations.IsQueryWithKey
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by cui.yan on 2018/7/9.
 *
 * * ！!!!!!!!!！整个流程使用可参考!!!!!!!!!!!!
 * @see DBHelper
 *
 *
 * 1.Dao继承BaseDao ，其中增删改已经包含，只需要自己写查询（受限于注解中属性只能为常量，无法在baseDao中动态指定表）
 * @see BaseDao
 *
 * 2.配合rxjava使用，主要注意2种observable类型，可跟据需求选
 * @see Flowable  只要数据库里的记录改变了，就会通知他的observer，一直生效，可用于需要监听改变ui等场景。
 * @see Single    查询到记录，通知一次observer，只生效一次
 *
 * 3.dao，需要注解 @Dao  并且为抽象类。
 * 表名可以直接敲对应entity类名, where查询可以直接用参数名
 */

@Dao
abstract class JokeInfoDao : BaseDao<JokeInfo>() {
//WHERE isFavorite = 'true'
    @Query("SELECT * FROM JokeInfo ORDER BY `index` DESC")
    abstract  fun getAllRecords(): DataSource.Factory<Int, JokeInfo>

    @IsQuery
    @Query("SELECT * FROM JokeInfo ORDER BY `index` DESC")
    abstract  fun getAllRecords2():Flowable<List<JokeInfo>>
    /**
     * 如需条件查询，方法里的实参就是select语句条件值
     */
    @IsQueryWithKey
    @Query("SELECT * FROM JokeInfo WHERE id = :id")
    abstract fun getJokeById(id:String?): Single<JokeInfo>


    @Query("DELETE FROM JokeInfo")
    protected abstract fun deleteAll()

    suspend fun deleteAllRecords(){
        withContext(Dispatchers.IO){
            deleteAll()
        }
    }


}