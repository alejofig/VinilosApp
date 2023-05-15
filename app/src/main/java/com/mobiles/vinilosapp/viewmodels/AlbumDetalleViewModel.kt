package com.mobiles.vinilosapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mobiles.vinilosapp.models.Album
import com.mobiles.vinilosapp.network.NetworkServiceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumDetalleViewModel(application: Application, albumId: Int) :  AndroidViewModel(application) {
    var applicationViewModel = application
    val id:Int = albumId
    private val _album = MutableLiveData<Album>()

    val album: LiveData<Album>
        get() = _album

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
                    var potentialResp = CacheManager.getInstance(applicationViewModel.applicationContext).getAlbumDetail(id)
                    if (potentialResp.albumId == 0){
                        var data = NetworkServiceAdapter.getInstance(getApplication()).getAlbum(id)
                        CacheManager.getInstance(applicationViewModel.applicationContext).addAlbumDetail(id, data)
                        _album.postValue(data)
                    }
                    else{
                        Log.d("Cache decision", "return Album Detail from cache")
                        _album.postValue(potentialResp)
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
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetalleViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumDetalleViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}