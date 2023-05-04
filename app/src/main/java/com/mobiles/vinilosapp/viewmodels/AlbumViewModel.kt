package com.mobiles.vinilosapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.android.volley.VolleyError
import com.mobiles.vinilosapp.models.Album
import com.mobiles.vinilosapp.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumViewModel(application: Application) :  AndroidViewModel(application) {

    private val _albums = MutableLiveData<List<Album>>()

    val albums: LiveData<List<Album>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        NetworkServiceAdapter.getInstance(getApplication()).getAlbums({
            _albums.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    fun createAlbum(album: Album, onSuccess: () -> Unit, onError: (error: VolleyError) -> Unit) {

        val albumJson = JSONObject()
        albumJson.put("name", album.name)
        albumJson.put("cover", album.cover)
        albumJson.put("releaseDate", album.releaseDate)
        albumJson.put("description", album.description)
        albumJson.put("genre", album.genre)
        albumJson.put("recordLabel", album.recordLabel)

        NetworkServiceAdapter.getInstance(getApplication()).postAlbum(albumJson, { resp ->
            onSuccess()
            refreshDataFromNetwork()
        }, { error ->
            onError(error)
        })
    }


    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }

}