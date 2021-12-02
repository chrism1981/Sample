package com.joker.utils.dataBinding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.joker.utils.dataBinding.BaseBindingHolder
/**
 *
 * @param dataBindingId BR in databinding xml
 * @param layoutResId Layout id
 * @param data data
 */
abstract class BaseBindingRecycleAdapterIAbstract<T>(private val dataBindingId: Int, private val layoutResId: Int,
                                                     private var diffCallback: DiffUtil.ItemCallback<T> ) :
    PagedListAdapter<T,BaseBindingHolder?>(diffCallback) {
    private var presenter: Any? = null
    private var pid = 0
    private var call: ((BaseBindingHolder, Int)-> Unit)? = null
    fun setPresenter(presenter: Any?, pid: Int) {
        this.presenter = presenter
        this.pid = pid
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutResId, parent, false
        )
        val holder = BaseBindingHolder(binding.getRoot())
        holder.binding = binding
        myOnCreateViewHolder(parent, viewType, holder)
        return holder
    }

    abstract fun myOnCreateViewHolder(parent: ViewGroup?, viewType: Int, holder: BaseBindingHolder?)

    fun setOnBinder(call: (BaseBindingHolder, Int)-> Unit){
        this.call = call
    }

    override fun onBindViewHolder(holder: BaseBindingHolder, position: Int) {
        holder.binding?.setVariable(dataBindingId, getItem(position))
        if (presenter != null) {
            holder.binding?.setVariable(pid, presenter)
        }
        holder.binding?.executePendingBindings()
        myOnbinde(holder, position)
        call?.invoke(holder,position)
    }

    abstract fun myOnbinde(holder: BaseBindingHolder?, position: Int)



}
