package com.mobiles.vinilosapp.ui.collectors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiles.vinilosapp.R
import com.mobiles.vinilosapp.databinding.ArtistFragmentBinding
import com.mobiles.vinilosapp.databinding.CollectorFragmentBinding
import com.mobiles.vinilosapp.models.Artist
import com.mobiles.vinilosapp.models.Collector
import com.mobiles.vinilosapp.ui.adapters.ArtistsAdapter
import com.mobiles.vinilosapp.ui.adapters.CollectorAdapter
import com.mobiles.vinilosapp.ui.collectors.CollectorDecoration
import com.mobiles.vinilosapp.ui.artists.ArtistDecoration
import com.mobiles.vinilosapp.viewmodels.ArtistViewModel
import com.mobiles.vinilosapp.viewmodels.CollectorViewModel

class CollectorFragment : Fragment() {

    private var _binding: CollectorFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CollectorViewModel
    private var viewModelAdapter: CollectorAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = CollectorFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = CollectorAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.collectorsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)
        val largePadding = 10
        val smallPadding = 0
        recyclerView.addItemDecoration(CollectorDecoration(largePadding, smallPadding))
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, CollectorViewModel.Factory(activity.application))
            .get(CollectorViewModel::class.java)

        viewModel.collectors.observe(viewLifecycleOwner, Observer<List<Collector>> {
            it.apply {
                viewModelAdapter!!.collectors = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}