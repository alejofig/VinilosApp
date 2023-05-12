package com.mobiles.vinilosapp.viewmodels

import CacheManager
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.volley.VolleyError
import com.mobiles.vinilosapp.models.Album
import com.mobiles.vinilosapp.network.NetworkServiceAdapter
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumViewModel(application: Application) :  AndroidViewModel(application) {


    val applicationViewModel = application

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
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var potentialResp = CacheManager.getInstance(applicationViewModel.applicationContext).getListFromCache("Albums")
                    if (potentialResp.isEmpty()) {
                        Log.d("Cache decision", "return Albums from NetworkService")
                        var data = NetworkServiceAdapter.getInstance(getApplication()).getAlbums()
                        CacheManager.getInstance(applicationViewModel.applicationContext).addListToCache("Albums", data)
                        _albums.postValue(data)
                    } else {
                        Log.d("Cache decision", "return Albums from cache")
                        _albums.postValue(potentialResp as List<Album>?)
                    }

                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
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