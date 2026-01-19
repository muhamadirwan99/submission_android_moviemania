package com.dicoding.moviemania.utils

import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

fun Int.toCompactNumber(): String {
    // 1. Cek jika angka kurang dari 1000, langsung kembalikan aslinya
    if (this < 1000) return this.toString()

    // 2. Daftar suffix (k = Ribu, M = Juta, B = Miliar)
    val suffixes = charArrayOf(' ', 'k', 'M', 'B', 'T')

    // 3. Hitung kelipatan 1000 (Logaritma basis 1000)
    // Contoh: 1.500 -> exp = 1 (k), 1.000.000 -> exp = 2 (M)
    val exp = (log10(this.toDouble()) / log10(1000.0)).toInt()

    // 4. Hitung nilai yang dibagi
    // Contoh: 1500 / 1000^1 = 1.5
    val value = this / 1000.0.pow(exp.toDouble())

    // 5. Format tampilan (1 desimal)
    // Contoh: 1.5k, 1.2M
    val decimalFormat = DecimalFormat("#.#")
    return String.format("%s%c", decimalFormat.format(value), suffixes[exp])
}

// Opsional: Versi Double (jika inputnya popularity yang tipe Double)
fun Double.toCompactNumber(): String {
    if (this < 1000) return DecimalFormat("#").format(this)
    val exp = (log10(this) / log10(1000.0)).toInt()
    val value = this / 1000.0.pow(exp.toDouble())
    val suffixes = charArrayOf(' ', 'k', 'M', 'B', 'T')
    val decimalFormat = DecimalFormat("#.#")
    return String.format("%s%c", decimalFormat.format(value), suffixes[exp])
}