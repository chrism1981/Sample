package com.joker.ui.main

import android.R.attr
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.joker.BASE_URL
import com.joker.R
import com.joker.databinding.FragmentMainBinding
import com.joker.viewModel.preView.SharedPreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import android.content.Intent
import android.R.attr.phoneNumber
import android.R.id

import android.R.id.message
import android.content.Context
import android.telephony.SmsManager
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.joker.MainActivity
import com.joker.utils.toast
import android.content.pm.ResolveInfo

import android.provider.Telephony

import android.os.Build
import androidx.annotation.NonNull
import android.R.attr.phoneNumber
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class PreviewFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val sharedPreViewModel: SharedPreViewModel by activityViewModels()
    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.apply {
            mainFragmentViewModel = sharedPreViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initClick()

    }

    private fun initClick() {
        favorite.setOnClickListener {
            context?.let { it1 -> sharedPreViewModel.addToFavorite(it1) }
        }

        share.setOnClickListener {
            activity?.let { it1 ->
                sharedPreViewModel.shareJoke(it1)
            }
        }
        email.setOnClickListener {
            val uri = Uri.parse("mailto:squall8111@gmail.com")//address can be edit in email app
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share a joke") // 主题
            intent.putExtra(Intent.EXTRA_TEXT, sharedPreViewModel.jokeInfo.value?.joke) // 正文
            startActivity(Intent.createChooser(intent, "please choose email app"))
        }

        message.setOnClickListener {
            XXPermissions.with(this)
                .permission(Permission.SEND_SMS)
                .request(object : OnPermissionCallback {
                    override fun onGranted(permissions: List<String>, all: Boolean) {
                        if (all) {
//                            toast("get permission successfully")
                            senSms()
                        } else {
                            toast("fail get permission")
                        }
                    }

                    override fun onDenied(permissions: List<String>, never: Boolean) {
                        if (never) {
                            toast("fail get permission,please grant mannuly")
                            XXPermissions.startPermissionActivity(activity, permissions)
                        } else {
                            toast("fail get permission")
                        }
                    }
                })


        }
    }

    private fun senSms() {
        val defaultSmsPackageName =
            Telephony.Sms.getDefaultSmsPackage(activity) //Need to change the build to API 19
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT, sharedPreViewModel.jokeInfo?.value?.joke)

        if (defaultSmsPackageName != null) {
            sendIntent.setPackage(defaultSmsPackageName)
        }
        requireActivity().startActivity(sendIntent)
    }

}