package com.mobiles.vinilosapp.viewmodels

import CacheManager
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.volley.VolleyError
import com.mobiles.vinilosapp.models.Collector
import com.mobiles.vinilosapp.network.NetworkServiceAdapter
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorViewModel(application: Application) :  AndroidViewModel(application) {

    private val applicationViewModel = application

    private val _collectors = MutableLiveData<List<Collector>>()

    val collectors: LiveData<List<Collector>>
        get() = _collectors

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
                    val potentialResp = CacheManager.getInstance(applicationViewModel.applicationContext).getListFromCache("Collectors")
                    if (potentialResp.isEmpty()) {
                        Log.d("Cache decision", "return Collectors from NetworkService")
                        val data = NetworkServiceAdapter.getInstance(getApplication()).getCollectors()
                        CacheManager.getInstance(applicationViewModel.applicationContext).addListToCache("Collectors", data)
                        _collectors.postValue(data)
                    } else {
                        Log.d("Cache decision", "return Collectors from cache")
                        _collectors.postValue(potentialResp as List<Collector>?)
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
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }

}