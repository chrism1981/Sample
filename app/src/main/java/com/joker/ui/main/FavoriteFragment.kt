package com.joker.ui.main

import ambimi.rogue.core.data.PositionReq
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.joker.R
import com.joker.data.dto.*
import com.joker.databinding.FavoriteFragmentBinding
import com.joker.databinding.ItemJokeListBinding
import com.joker.utils.dataBinding.BaseBindingHolder
import com.joker.utils.dataBinding.adapter.BaseBindingRecycleAdapter
import com.joker.utils.dataBinding.adapter.BaseBindingRecycleAdapterNoPaging
import com.joker.viewModel.preView.SharedPreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.favorite_fragment.*
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*


/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FavoriteFragmentBinding
    private val sharedPreViewModel: SharedPreViewModel by activityViewModels()
    lateinit var adapter: BaseBindingRecycleAdapterNoPaging<PositionData>
    private var token: String? = null
    private var bookTS: String = ""
    private var booked = false
    private val desiredMonth = 9
    private val afterDay = 5
    private val peroid = 35000L
    companion object{
        const val delayForMultiplePosition = 16000L
    }

    private var time1 = 0L
    private var timePassed = 0L

    private var positionData: PositionData? = null
    private var timer = Timer()
    private var timerTask:TimerTask? = null

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        Log.e("sms  ", event?: "null")

        token?.let {
            CoroutineScope(Dispatchers.IO).launch{
                val codes = VerifyCodeReq(code = event, bookedTs = bookTS)
                if (sharedPreViewModel.verifyCode(it,codes)){
                    if(sharedPreViewModel.book(it, BookReq())){
                        withContext(Dispatchers.Main){
                            sharedPreViewModel.logout(it)
                            result.text = "报名时间: " + positionData?.appointmentDt?.date +"  "+ positionData?.startTm
                        }
                    }else{
                        createTimerTask()
                        timer = Timer()
                        timer.schedule(timerTask,500,peroid)
                    }
                }else{
                    createTimerTask()
                    timer = Timer()
                    timer.schedule(timerTask,500,peroid)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.favorite_fragment, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            jokeListViewModel = sharedPreViewModel
        }
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
        login()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this);
        token?.let {
            CoroutineScope(Dispatchers.IO).launch {
                sharedPreViewModel.logout(it)
                cancelTimer()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun rand(start: Int, end: Int): Int {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(end - start + 1) + start
    }


    private fun createTimerTask(){
        timerTask = object : TimerTask() {
            override fun run() {
                token?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        if(time1 == 0L ){
                            time1 = System.currentTimeMillis()
                        }else{
                            timePassed = System.currentTimeMillis() - time1
                        }

                        if(timePassed > ((1000 * 60 * 18)+(rand(0,543) * 1000 ))){
                            cancelTimer()
                            sharedPreViewModel.logout(it)
                            delay(1000* 60 * 3)
                            login()
                            timePassed = 0L
                            time1 = 0L
                            return@launch
                        }
                        val dele = rand(0,1551).toLong()
                        Log.e("delay long: ",dele.toString())
                        delay(dele)

                        val requestArray = arrayOf(
                            PositionReq(aPosID = 274),
                            PositionReq(aPosID = 73)
                        )

                        requestArray.forEach { req->
                            val list = sharedPreViewModel.queryPosition(it,
                                { code->
                                    if (code != 403)
                                        login()
                                    cancelTimer()
                                }, req)
                            if(processPositionList(list, it)){
                                try {
                                    cancel()
                                } catch (e: Exception) {
                                }
                                return@launch
                            }
                            delay(delayForMultiplePosition)
                        }
                    }
                }

            }
        }
    }

    private suspend fun processPositionList(
        list: List<PositionData?>,
        it: String
    ):Boolean {
        if (list.isNotEmpty()) {
            list.forEach { po ->
                val dates = po?.appointmentDt?.date
                dates?.apply {
                    val year = substring(0, 4).toInt()
                    val month = substring(5, 7).toInt()
                    val day = substring(8, this.length).toInt()
                    Log.e("date", " $year  $month  $day")
                    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    bookTS = sdf.format(Date())

                    if (month == desiredMonth && day >= afterDay && year == 2022) {
    //                                    if (month == 1 && day > 13 && year == 2023 ){
                        po.bookedTs = bookTS
                        Log.e("date bookts", bookTS)
                        if (sharedPreViewModel.lock(it, po)) {
                            val sendc = SendCodeReq(bookedTs = bookTS)
                            positionData = po
                            sharedPreViewModel.sendCode(it, sendc)
                            cancelTimer()
                            return true
                        }
                    }

                }
            }
    //                        adapter = BaseBindingRecycleAdapterNoPaging(BR.jokeInfo, R.layout.item_joke_list,
    //                            list)
    //                        withContext(Dispatchers.Main){
    //                            joke_list.adapter = adapter
    //                        }
        }
        return false

    }


    fun login(){
        CoroutineScope(Dispatchers.IO).launch {
            token = sharedPreViewModel.getJokeFromServer(requireContext())
            Log.e("token :", token ?: "null")
            token?.let {
                createTimerTask()
                timer = Timer()
                timer.schedule(timerTask,500,peroid)
            }
        }
    }


    /**
     * when add new joke ,delete a joke, refresh the list
     */
    private fun updateList(
        it: FragmentActivity,
        adapter: BaseBindingRecycleAdapter<JokeInfo>
    ) = CoroutineScope(Dispatchers.IO).launch {
        sharedPreViewModel.getLocalJokes(it)
        withContext(Dispatchers.Main) {
            sharedPreViewModel.jokes?.observe(this@FavoriteFragment, adapter::submitList)
        }
    }

    /**
     * slide delete item from database
     */
    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val bind = (viewHolder as BaseBindingHolder).binding as ItemJokeListBinding
                activity?.let {
//                    sharedPreViewModel.deleteJoke(bind.jokeInfo!!, it)
//                    bind.root.postDelayed({ updateList(it, adapter) }, 300)
                }

            }
        }).attachToRecyclerView(joke_list)
    }

    private fun cancelTimer() {
        try {
            timer.cancel()
        } catch (e: Exception) {
        }
        timer = Timer()
    }

}


