package com.dicoding.moviemania.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.withDateFormat(): String {
    // 1. Format input (sesuai data dari API: "2023-03-22")
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    // 2. Format output yang diinginkan ("22 March 2023")
    // Gunakan Locale.US untuk bahasa Inggris ("March")
    // Gunakan Locale("id", "ID") untuk bahasa Indonesia ("Maret")
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.US)

    return try {
        val date = inputFormat.parse(this)
        outputFormat.format(date ?: return this)
    } catch (e: Exception) {
        // Jika gagal parsing, kembalikan string aslinya
        this
    }
}