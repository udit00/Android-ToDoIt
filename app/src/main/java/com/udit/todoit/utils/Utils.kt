package com.udit.todoit.utils

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
        return if(!ipv4.isNullOrBlank()) ipv4
        else if(!ipv6.isNullOrBlank()) ipv6
        else "NA"
    }
}