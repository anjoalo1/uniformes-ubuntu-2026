package com.example.confeccionesjetpackcompose.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class CalculaFechas {

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerNewFecha2():List<Any>{
        val horaColombia = ZonedDateTime.now(ZoneId.of("America/Bogota"))
        //val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.SSSSSS")
        //val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSXXX")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS", Locale.forLanguageTag("es-ES"))
        val fechaFormateada = horaColombia.format(formatter)

        //println("Fecha formateada como string: $fechaFormateada")
        //print("\nDe fecha iso string a formato  $fechaFormateada")


        //val fechaIso = "2025-03-09T21:51:41.208"
        val mothSpanish = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
        val formatter2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val ldtFormatoIso = LocalDateTime.parse(fechaFormateada, formatter2)
        val ldtString = ldtFormatoIso.toString();
        //println("\n-fecha formato iso: $ldtFormatoIso")
        val anio = ldtFormatoIso.year
        val mes = ldtFormatoIso.monthValue
        val dia = ldtFormatoIso.dayOfMonth
        val nano = ldtFormatoIso.nano
        val hora = ldtFormatoIso.hour
        val minute = ldtFormatoIso.minute
        val segundo = ldtFormatoIso.second

        val mili = (nano/1000000).toInt()

        val mesTexto = mothSpanish[ldtFormatoIso.monthValue-1].uppercase()
        //println("\n year: $anio \n moth: $mes \n day: $dia \n mes: $mesTexto" )

        val myList = listOf(dia.toString(), mes.toString(), anio.toString(), ldtFormatoIso.toString(), fechaFormateada.toString(), dia.toString(), hora.toString(), minute.toString(), segundo.toString(), mili.toString())
        //return horaColombia.toString()
        return myList

    }

}