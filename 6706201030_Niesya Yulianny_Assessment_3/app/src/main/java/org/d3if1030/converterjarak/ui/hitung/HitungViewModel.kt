package org.d3if1030.converterjarak.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if1030.converterjarak.db.DataDao
import org.d3if1030.converterjarak.db.DataEntity
import org.d3if1030.converterjarak.model.Hasil

class HitungViewModel(private val db: DataDao) : ViewModel() {

    private val hasilConvert = MutableLiveData<Hasil?>()

    fun hitung(input: Float) {
        val hasil = input * 1000
        hasilConvert.value = Hasil(hasil)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dataHasil = DataEntity(
                    input = input,
                    hasil = hasil
                )
                db.insert(dataHasil)
            }
        }
    }
    fun getHasil(): LiveData<Hasil?> = hasilConvert
}