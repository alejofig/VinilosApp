package com.mobiles.vinilosapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mobiles.vinilosapp.models.Artist
import com.mobiles.vinilosapp.network.NetworkServiceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistViewModel(application: Application) :  AndroidViewModel(application) {

    val applicationViewModel = application

    private val _artists = MutableLiveData<List<Artist>>()

    val artists: LiveData<List<Artist>>
        get() = _artists

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
                    var potentialResp = CacheManager.getInstance(applicationViewModel.applicationContext).getListFromCache("Artists")
                    if (potentialResp.isEmpty()) {
                        Log.d("Cache decision", "return Artists from NetworkService")
                        var data = NetworkServiceAdapter.getInstance(getApplication()).getArtists()
                        CacheManager.getInstance(applicationViewModel.applicationContext).addListToCache("Artists", data)
                        _artists.postValue(data)
                    } else {
                        Log.d("Cache decision", "return Artists from cache")
                        _artists.postValue(potentialResp as List<Artist>?)
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

    class Factory(val app: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}