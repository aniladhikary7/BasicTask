package com.anil.tailwebstask.utilities

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {
    private val mSharedPreferences: SharedPreferences

    fun getString(key: String?, defaultValue: String?): String? {
        return mSharedPreferences.getString(key, defaultValue)
    }

    fun setString(key: String?, value: String?) {
        val editor = mSharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    fun setInt(key: String?, value: Int) {
        val editor = mSharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getBool(key: String?, defaultValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(key, defaultValue)
    }

    fun setBool(key: String?, value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun clearPref() {
        val editor = mSharedPreferences.edit()
        editor.clear()
        editor.commit()
    }

    companion object {
        private var mPrefManager: PrefManager? = null
        private const val PREFERENCE_FILE_KEY =
            "com.anil.tailwebstask.PREFERENCE_FILE_KEY"

        fun getInstance(context: Context): PrefManager? {
            if (mPrefManager == null) {
                mPrefManager = PrefManager(context)
            }
            return mPrefManager
        }
    }

    init {
        mSharedPreferences = context.getSharedPreferences(
            PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE
        )
    }
}