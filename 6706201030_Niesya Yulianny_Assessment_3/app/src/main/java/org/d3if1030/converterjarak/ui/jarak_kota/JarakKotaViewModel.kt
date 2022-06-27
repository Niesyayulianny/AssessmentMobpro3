package org.d3if1030.converterjarak.ui.jarak_kota

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if1030.converterjarak.MainActivity
import org.d3if1030.converterjarak.model.JarakKota
import org.d3if1030.converterjarak.network.ApiStatus
import org.d3if1030.converterjarak.network.JarakKotaApi
import org.d3if1030.converterjarak.network.UpdateWorker
import java.util.concurrent.TimeUnit

class JarakKotaViewModel: ViewModel() {
    private val data = MutableLiveData<List<JarakKota>>()
    private val status = MutableLiveData<ApiStatus>()
    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(JarakKotaApi.service.getResult())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }
    fun getData(): LiveData<List<JarakKota>> = data

    fun getStatus(): LiveData<ApiStatus> = status

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            MainActivity.CHANNEL_ID,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}