package com.example.marvelchallenge.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.marvelchallenge.R
import com.example.marvelchallenge.app.vm.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private val viewModel by viewModels<CharactersViewModel>()

    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: CharactersAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_characters, container, false)

        recycler = v.findViewById(R.id.recycler)

        swipe = v.findViewById(R.id.swipe)

        navController = findNavController()

        adapter = CharactersAdapter(navController)
        recycler.adapter = adapter

        adapter.addLoadStateListener {
            swipe.isRefreshing = it.refresh is LoadState.Loading || it.append is LoadState.Loading
        }

        swipe.setOnRefreshListener { adapter.refresh() }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.characterList.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
