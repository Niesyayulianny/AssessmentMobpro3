package org.d3if1030.converterjarak.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if1030.converterjarak.db.DataDao

class HistoriViewModel(private val db: DataDao) : ViewModel() {
    val data = db.getLastJajargenjang()
    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }

}