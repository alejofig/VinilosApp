package com.mobiles.vinilosapp.ui.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.mobiles.vinilosapp.models.Artist
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiles.vinilosapp.databinding.ArtistDetailFragmentBinding
import com.mobiles.vinilosapp.ui.adapters.ArtistDetailAdapter
import com.mobiles.vinilosapp.viewmodels.ArtistDetailViewModel

class ArtistDetailFragment: Fragment() {
    private var _binding: ArtistDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArtistDetailViewModel
    private var viewModelAdapter: ArtistDetailAdapter? = null
    private var artistId: Int = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ArtistDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = ArtistDetailAdapter()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.artistsDetailRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artistId = it.getInt("id_artist")
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, ArtistDetailViewModel.Factory(activity.application, artistId))
            .get(ArtistDetailViewModel::class.java)
        viewModel.artist.observe(viewLifecycleOwner, Observer<Artist> {
            it.apply {
                viewModelAdapter!!.artist = this
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