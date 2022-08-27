package com.joker

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.joker.data.dto.JokeInfo
import com.joker.databinding.ActivityMainBinding
import com.joker.ui.main.SectionsPagerAdapter
import com.joker.utils.dataBase.DBHelper
import com.joker.viewModel.preView.SharedPreViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.robin.lazy.sms.SmsObserver
import com.robin.lazy.sms.SmsResponseCallback
import com.robin.lazy.sms.VerificationCodeSmsFilter
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SmsResponseCallback{

    private lateinit var binding: ActivityMainBinding
    private val sharedPreViewModel by viewModels<SharedPreViewModel>()
    private var viewPager: ViewPager? = null
    private lateinit var smsObserver: SmsObserver
    private var code: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = binding.viewPager
        viewPager?.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

        smsObserver = SmsObserver(this, this, VerificationCodeSmsFilter("8422"))
        smsObserver.registerSMSObserver()


        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_SMS)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) { /* ... */
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) { /* ... */
                }
            }).check()
    }



    override fun onDestroy() {
        super.onDestroy()
        smsObserver.unregisterSMSObserver()
        exitProcess(0)

    }

    override fun onCallbackSmsContent(smsContent: String?) {
        smsContent?.let {
            if(code != it){
                code = it
                val dao = DBHelper.getDataBase(this)?.jokeDao()

                dao?.queryWithKey<JokeInfo>(code,{
                    Toast.makeText(this,"already exist codes", Toast.LENGTH_SHORT).show()
                },{
                    val joke = JokeInfo()
                    joke.id = code
                    joke.joke = ""
                    joke.status = 0
                    dao.insert(joke)
                    EventBus.getDefault().post(it)
                })
            }
        }

    }
}