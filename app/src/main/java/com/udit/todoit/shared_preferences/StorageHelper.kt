package com.udit.todoit.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import com.google.gson.Gson
import com.udit.todoit.entry_point.application.MyApp
import com.udit.todoit.ui.login.model.LoginModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.log

class StorageHelper @Inject constructor(val myApp: MyApp) {

    private var sharedPreferences: SharedPreferences =
        myApp.getSharedPreferences(this@StorageHelper.javaClass.simpleName, Context.MODE_PRIVATE)
    private var editor: Editor = sharedPreferences.edit()

    companion object {
        const val STORAGE_ERROR_TAG = "ERROR_TAG"
        const val LOGIN_PREF_TAG: String = "LOGIN_PREF_TAG"
    }

    fun getString(key: String): String {
        var str: String? = STORAGE_ERROR_TAG
        try {
            str = sharedPreferences.getString(key, STORAGE_ERROR_TAG)
        } catch (ex: Exception) {
            Log.e(this@StorageHelper.javaClass.simpleName, ex.message.toString())
            str = STORAGE_ERROR_TAG
        }
        return if (str.isNullOrBlank()) STORAGE_ERROR_TAG else str
    }

    fun setString(key: String, value: String): Boolean {
        var isSaved = false
        try {
            editor.putString(key, value)
            isSaved = editor.commit()
        } catch (ex: Exception) {
            Log.e(this@StorageHelper.javaClass.simpleName, ex.message.toString())
            isSaved = false
        }
        return isSaved
    }

    fun remove(key: String): Boolean {
        var isRemoved: Boolean = false
        try {
            editor.remove(key)
            isRemoved = editor.commit()
        } catch (ex: Exception) {
            isRemoved = false;
        }
        return isRemoved
    }

    fun getLoginData(): LoginModel? {
        var loginModel: LoginModel? = null
        try {
            val serializedLoginStr = getString(LOGIN_PREF_TAG)
            if(serializedLoginStr == LOGIN_PREF_TAG) {
                throw Exception(STORAGE_ERROR_TAG)
            } else {
                val gson = Gson()
                loginModel = gson.fromJson(serializedLoginStr, LoginModel::class.java)
            }
        } catch (ex: Exception) {
            loginModel = null
        }
        return loginModel
    }
}
