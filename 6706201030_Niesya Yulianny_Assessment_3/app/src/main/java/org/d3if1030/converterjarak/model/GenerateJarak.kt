package org.d3if1030.converterjarak.model

import org.d3if1030.converterjarak.db.DataEntity

fun DataEntity.convert(): Hasil {
    val hasil = input * 1000

    return Hasil(hasil)
}