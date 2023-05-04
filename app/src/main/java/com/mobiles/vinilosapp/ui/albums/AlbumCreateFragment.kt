package com.mobiles.vinilosapp.ui.albums

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputEditText
import com.mobiles.vinilosapp.databinding.AlbumCreateFragmentBinding
import com.mobiles.vinilosapp.models.Album
import com.mobiles.vinilosapp.viewmodels.AlbumViewModel

class AlbumCreateFragment:  Fragment() {

    private var _binding: AlbumCreateFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = AlbumCreateFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireNotNull(this.activity) {
        }
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application))
            .get(AlbumViewModel::class.java)

        setDropDowns()

        _binding?.albumCreateButton?.setOnClickListener(createAlbumListener())

    }

    private fun setDropDowns() {
        val adapterGenre = ArrayAdapter(
            requireContext(), R.layout.simple_spinner_dropdown_item, arrayListOf("Classical", "Salsa", "Rock", "Folk")
        )
        val adapterRecord = ArrayAdapter(
            requireContext(), R.layout.simple_spinner_dropdown_item, arrayListOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
        )

        _binding?.txtAlbumDisc?.setAdapter(adapterRecord)
        _binding?.txtAlbumGenre?.setAdapter(adapterGenre)
    }

    private fun createAlbumListener() : View.OnClickListener {

        return View.OnClickListener { view ->

            val nameTxt : TextInputEditText? = _binding?.txtAlbumName
            val imageTxt : TextInputEditText? = _binding?.txtAlbumImage
            val datePkr : DatePicker? = _binding?.txtAlbumDate
            val descTxt : TextInputEditText? = _binding?.txtAlbumDesc
            val discTxt : AutoCompleteTextView? = _binding?.txtAlbumDisc
            val genreTxt : AutoCompleteTextView? = _binding?.txtAlbumGenre

            val album = Album(
                albumId =  null,
                name = nameTxt?.text.toString(),
                cover = imageTxt?.text.toString(),
                releaseDate = "1984-08-01T00:00:00-05:00",
                description = descTxt?.text.toString(),
                genre = genreTxt?.text.toString(),
                recordLabel = discTxt?.text.toString(),
            )

            viewModel.createAlbum(album,
                onSuccess = {

                    NavHostFragment.findNavController(this@AlbumCreateFragment).navigateUp()

            }, onError = { error ->

                    NavHostFragment.findNavController(this@AlbumCreateFragment).navigateUp()
            })

            _binding?.albumCancelButton?.setOnClickListener {
                NavHostFragment.findNavController(this@AlbumCreateFragment).navigateUp()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}