package com.joker.utils.dataBinding.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.joker.utils.dataBinding.BaseBindingHolder


open class BaseBindingRecycleAdapterNoPaging<T>(dataBindingId: Int,layoutResId: Int,  data: List<T?>) :
    BaseBindingRecycleAdapterNoPagingIAbstract<T>(dataBindingId, layoutResId, data) {
    override fun myOnCreateViewHolder(parent: ViewGroup?, viewType: Int, holder: BaseBindingHolder?) {}
    override fun myOnbinde(holder: BaseBindingHolder?, position: Int) {}
}
