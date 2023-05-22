package com.mobiles.vinilosapp.viewmodels

import CacheManager
import android.app.Application
import androidx.lifecycle.*
import com.mobiles.vinilosapp.models.Album
import com.mobiles.vinilosapp.network.NetworkServiceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AlbumCreateViewModel(application: Application) :  AndroidViewModel(application) {

    var applicationViewModel = application

    private var _album = MutableLiveData<Album>()

    val album : LiveData<Album>
        get() = _album

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown



    fun createAlbum(album: Album) {

        val albumJson = JSONObject()
        albumJson.put("name", album.name)
        albumJson.put("cover", album.cover)
        albumJson.put("releaseDate", album.releaseDate)
        albumJson.put("description", album.description)
        albumJson.put("genre", album.genre)
        albumJson.put("recordLabel", album.recordLabel)

        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO){
                    val newAlbum = NetworkServiceAdapter.getInstance(getApplication()).postAlbum(albumJson)
                    _album.postValue(newAlbum)
                }

                withContext(Dispatchers.IO){
                    val listAlbum = NetworkServiceAdapter.getInstance(getApplication()).getAlbums()
                    CacheManager.getInstance(applicationViewModel.applicationContext).updateListToCache("Albums", listAlbum)
                }



            }
        } catch (e: Exception){
            _eventNetworkError.value = true
        }

    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory (val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumCreateViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}