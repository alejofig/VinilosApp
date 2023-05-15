package com.mobiles.vinilosapp.ui.albums

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiles.vinilosapp.R
import com.mobiles.vinilosapp.databinding.AlbumFragmentBinding
import com.mobiles.vinilosapp.models.Album
import com.mobiles.vinilosapp.ui.adapters.AlbumsAdapter
import com.mobiles.vinilosapp.viewmodels.AlbumViewModel

class AlbumFragment : Fragment() {

    private var _binding: AlbumFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = AlbumFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumsAdapter()

        _binding?.addAlbum!!.setOnClickListener { click ->
            val action = AlbumFragmentDirections.actionNavigationAlbumsToNavigationAlbumCreateFragment2()
            _binding?.root!!.findNavController().navigate(action)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        val largePadding = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.grid_spacing_small)
        recyclerView.addItemDecoration(AlbumsDecoration(largePadding, smallPadding))

        binding.search.addTextChangedListener { userFilter ->
            val albumsFiltered = viewModel.albums.value?.filter { album ->
                album.name.lowercase().contains(userFilter.toString().lowercase())
            }

            viewModelAdapter?.updateAlbumList(albumsFiltered!!)

        }


    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application))
            .get(AlbumViewModel::class.java)
        viewModel.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
            it.apply {
                Log.d("Observer", "cambie")
                viewModelAdapter!!.albums = this
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