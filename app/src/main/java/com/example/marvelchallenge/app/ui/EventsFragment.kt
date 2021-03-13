package com.example.marvelchallenge.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelchallenge.R
import com.example.marvelchallenge.app.vm.EventsViewModel
import com.example.marvelchallenge.utils.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsFragment : Fragment(), EventsAdapter.SetIsExpandedEventCallBack {

    private lateinit var loadingDialog: LoadingDialog

    private lateinit var recycler: RecyclerView

    private val viewModel by viewModels<EventsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_events, container, false)

        recycler = v.findViewById(R.id.recycler)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = EventsAdapter(this)
        recycler.adapter = adapter

        viewModel.expandableEventList.observe(viewLifecycleOwner, Observer(adapter::submitList))

        loadingDialog = LoadingDialog()

        loadingDialog.observeLiveData(viewModel.loading, viewLifecycleOwner, childFragmentManager)
    }

    override fun setIsExpandedEvent(id: String) {
        viewModel.setEventIsExpanded(id)
    }
}
