package com.udit.todoit.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoField
import java.time.temporal.TemporalField
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Utils {

    private fun getIPv4(): String? {
        return ""
    }

    private fun getIPv6(): String? {
        return ""
    }

    fun getIpAddress(): String {
        val ipv4 = getIPv4()
        val ipv6 = getIPv6()
        return if (!ipv4.isNullOrBlank()) ipv4
        else if (!ipv6.isNullOrBlank()) ipv6
        else "NA"
    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun logger(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("EEE, MMM d HH:mm aaa", Locale.getDefault())
        return formatter.format(Date(millis))
    }

//    fun getCurrentDateTime(): Long {
//        return System.currentTimeMillis()
//    }
//
//    fun getCurrentDate(): Long {
//        return System.currentTimeMillis()
//    }
}
object DateUtils {
    fun getCalenderDate(): String {
        return Calendar.getInstance().toString()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun mergeDateAndTimeWithLong(dtLong: Long, timePickerState: TimePickerState): String {
        val localDateTime = getDateTimeStringFromLong(dtLong)
        val onlyDate = localDateTime.substring(0, 11)
        val hour = if(timePickerState.hour < 10) "0${timePickerState.hour}" else timePickerState.hour
        val min = if(timePickerState.minute < 10) "0${timePickerState.minute}" else timePickerState.minute
        val onlyTime = "${hour}:${min}:00.000"
        val dateTime = onlyDate+onlyTime
        Utils.logger("DATE TIME", dateTime)
        return dateTime
    }

    fun getCurrentDateTime(): LocalDateTime {
        return LocalDateTime.now()
    }

    fun getCurrentDate(): LocalDate {
        return LocalDate.now()
    }

    fun viewDateTime(dt: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = dt.format(formatter)
        return formatted
    }

    fun viewDateTimeFromString(dtStr: String): String {
        val dt = getDateTimeFromString(dtStr)
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = dt.format(formatter)
        return formatted
    }

    // WORKING // 2024-11-27T19:04:57.368 //
    fun getDateTimeFromString(dtStr: String): LocalDateTime {
        val localDateTime = LocalDateTime.parse(dtStr)
        return localDateTime
    }

    fun getHourFromString(localDt: LocalDateTime): Int {
        return localDt.hour
    }

    fun getMinuteFromString(localDt: LocalDateTime): Int {
        return localDt.minute
    }
    // Working
    fun getDateTimeStringFromLong(long: Long): String {
//        val tempLong: Long = System.currentTimeMillis()
        val tempLong: Long = long
        val date = Date(tempLong)
//        Log.d("DATE", date.toString())
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
//        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//        val dtStr = formatter.format(date)
//        val localDateTime = LocalDateTime.parse(date.toString(), formatter)
        return formatter.format(date)
    }

    // WORKING
    fun getTimeMillisFromString(dtStr: String): Long {
        val localDt = getDateTimeFromString(dtStr)
        return getTimeMillisFromDateTime(localDt)
    }


    fun getTimeMillisFromDateTime(localDt: LocalDateTime): Long {
//        val offSetId =
//        val zoneId = ZoneId.systemDefault()
//        val zdt: ZonedDateTime = localDt.atZone(zoneId)
        val zdt: ZonedDateTime = localDt.atZone(ZoneId.of("UTC"))
        return zdt.toInstant().toEpochMilli()
    }
}
