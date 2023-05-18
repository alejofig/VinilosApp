package com.mobiles.vinilosapp.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiles.vinilosapp.databinding.AlbumCommentFragmentBinding
import com.mobiles.vinilosapp.databinding.AlbumDetailFragmentBinding
import com.mobiles.vinilosapp.models.Album
import com.mobiles.vinilosapp.ui.adapters.AlbumDetailAdapter
import com.mobiles.vinilosapp.viewmodels.AlbumCommentViewModel
import com.mobiles.vinilosapp.viewmodels.AlbumDetalleViewModel

class AlbumCommentFragment:  Fragment() {

    private var _binding: AlbumCommentFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumCommentViewModel
    //private var viewModelAdapter: AlbumDetailAdapter? = null
    private var albumId: Int = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumCommentFragmentBinding.inflate(inflater, container, false)

        val view = binding.root
        //viewModelAdapter = AlbumDetailAdapter()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt("id_album")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, AlbumCommentViewModel.Factory(activity.application, albumId))
            .get(AlbumCommentViewModel::class.java)
        viewModel.album.observe(viewLifecycleOwner, Observer<Album> {
            it.apply {
               // viewModelAdapter!!.album = this
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