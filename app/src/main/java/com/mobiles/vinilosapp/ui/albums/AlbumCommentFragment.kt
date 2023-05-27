package com.mobiles.vinilosapp.ui.albums

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.mobiles.vinilosapp.R
import com.mobiles.vinilosapp.databinding.AlbumCommentFragmentBinding
import com.mobiles.vinilosapp.models.CollectorComment
import com.mobiles.vinilosapp.models.Comment
import com.mobiles.vinilosapp.viewmodels.AlbumDetalleViewModel


class AlbumCommentFragment:  Fragment() {

    private var _binding: AlbumCommentFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumDetalleViewModel
    private var albumId: Int = 100
    private var albumName: String = ""
    private var albumGenre: String = ""
    private var albumCover: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumCommentFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt("id_album")
            albumName = it.getString("name_album")!!
            albumCover= it.getString("cover_album")!!
            albumGenre = it.getString("genre_album")!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, AlbumDetalleViewModel.Factory(requireActivity().application, albumId))
            .get(AlbumDetalleViewModel::class.java)

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })


        _binding?.txtAlbumName?.setText( albumName)
        _binding?.txtAlbumGenre?.setText(albumGenre)

        Glide.with(view)
            .load(albumCover).apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(_binding!!.albumImage)
        _binding?.albumImage?.setContentDescription("Imagen del album "+albumName )

        val ratingBar: RatingBar? = _binding?.ratingBar

        _binding?.txtAlbumComment?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                _binding?.albumCommentButton?.isEnabled = s?.isNotEmpty() == true
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        _binding?.albumCommentButton?.setOnClickListener {

            val commentTxt : TextInputEditText? = _binding?.txtAlbumComment
            val rating = ratingBar!!.rating

            val comment = Comment(
                id =  null,
                description = commentTxt?.text.toString(),
                rating = rating.toInt(),
                collector = CollectorComment(id = 100)
            )

            viewModel.createComment(comment, albumId)
            val action = AlbumCommentFragmentDirections.actionAlbumCommentFragmentToNavigationAlbums()
            if (action != null) {
                _binding?.root!!.findNavController().navigate(action)
            }
        }
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