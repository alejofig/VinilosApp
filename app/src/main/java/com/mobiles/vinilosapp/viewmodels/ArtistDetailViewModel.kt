package com.mobiles.vinilosapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mobiles.vinilosapp.models.Artist
import com.mobiles.vinilosapp.network.NetworkServiceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistDetailViewModel(application: Application, artistId: Int) :  AndroidViewModel(application) {
    var applicationViewModel = application
    val id:Int = artistId
    private val _artist = MutableLiveData<Artist>()

    val artist: LiveData<Artist>
        get() = _artist

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
                    var potentialResp = CacheManager.getInstance(applicationViewModel.applicationContext).getArtistDetail(id)
                    if (potentialResp.artistId == 0){
                        var data = NetworkServiceAdapter.getInstance(getApplication()).getArtist(id)
                        CacheManager.getInstance(applicationViewModel.applicationContext).addArtistDetail(id, data)
                        _artist.postValue(data)
                    }
                    else{
                        Log.d("Cache decision", "return Artist Detail from cache")
                        _artist.postValue(potentialResp)
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
    class Factory(val app: Application, val artistId: Int) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistDetailViewModel(app, artistId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}
