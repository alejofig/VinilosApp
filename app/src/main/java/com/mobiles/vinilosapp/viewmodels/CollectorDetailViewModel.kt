package com.mobiles.vinilosapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mobiles.vinilosapp.models.Collector
import com.mobiles.vinilosapp.network.NetworkServiceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorDetailViewModel(application: Application, collectorId: Int): AndroidViewModel(application) {
    var applicationViewModel = application
    val id:Int = collectorId
    private val _collector = MutableLiveData<Collector>()

    val collector: LiveData<Collector>
        get() = _collector

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
                    var potentialResp = CacheManager.getInstance(applicationViewModel.applicationContext).getCollectorDetail(id)
                    if (potentialResp.id == 0){
                        var data = NetworkServiceAdapter.getInstance(getApplication()).getCollector(id)
                        CacheManager.getInstance(applicationViewModel.applicationContext).addCollectorDetail(id, data)
                        _collector.postValue(data)
                    }
                    else{
                        Log.d("Cache decision", "return Collector Detail from cache")
                        _collector.postValue(potentialResp)
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

    class Factory(val app: Application, val collectorId: Int) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorDetailViewModel(app,collectorId ) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }

}
