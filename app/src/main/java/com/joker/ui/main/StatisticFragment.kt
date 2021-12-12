package com.joker.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.joker.BR
import com.joker.R
import com.joker.data.dto.JokeInfo
import com.joker.data.dto.Words
import com.joker.databinding.FavoriteFragmentBinding
import com.joker.databinding.ItemJokeListBinding
import com.joker.utils.dataBinding.BaseBindingHolder
import com.joker.utils.dataBinding.adapter.BaseBindingRecycleAdapter
import com.joker.utils.dataBinding.adapter.BaseBindingRecycleAdapterNoPaging
import com.joker.viewModel.preView.SharedPreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.favorite_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.withContext

/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class StatisticFragment : Fragment() {
    private lateinit var binding: FavoriteFragmentBinding
    private val sharedPreViewModel: SharedPreViewModel by activityViewModels()
    lateinit var adapter: BaseBindingRecycleAdapterNoPaging<Words>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.favorite_fragment, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            jokeListViewModel = sharedPreViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            activity?.let { it1 ->
                val data = sharedPreViewModel.getWordsList(it1)
                withContext(Dispatchers.Main){
                    data?.observe(viewLifecycleOwner,
                        {
                            adapter = BaseBindingRecycleAdapterNoPaging(
                                BR.jokeInfoWord, R.layout.words_list,
                                it
                            )
                            joke_list.adapter = adapter
                        })
                }
            }
        }
    }




}