package com.ktl.tareasapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun obtenerFechaActual() : String{

    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    //dd : 01 , 15 ,  30
    //MM : 02 , 06 , 11
    //yyyy : 2025 , 2026 , 2027

    return formatter.format(Date())
    //"30/11/2026

}