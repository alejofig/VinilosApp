package com.mobiles.vinilosapp.ui.albums

import android.R
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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
    private lateinit var btnImage: ImageView
    private lateinit var pickMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickMediaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if(it != null){
                uri = it
                btnImage.setImageURI(uri)
            }else{
                //No image
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = AlbumCreateFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        btnImage = _binding?.albumCreateImage!!
        btnImage.setOnClickListener{

            pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

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

        _binding?.etDate?.setOnClickListener { showDatePickerDialog(activity) }

    }

    private fun showDatePickerDialog(activity : FragmentActivity) {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(activity.supportFragmentManager,  "datePicker")
    }

    fun onDateSelected(day:Int, month:Int, year:Int){
        _binding?.etDate?.setText("Has seleccionado el dia $day del mes $month de $year")
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
            val descTxt : TextInputEditText? = _binding?.txtAlbumDesc
            val discTxt : AutoCompleteTextView? = _binding?.txtAlbumDisc
            val genreTxt : AutoCompleteTextView? = _binding?.txtAlbumGenre

            val album = Album(
                albumId =  null,
                name = nameTxt?.text.toString(),
                cover = uri.toString(),
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