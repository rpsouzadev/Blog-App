package com.rpsouza.blogapp.data.store

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.rpsouza.blogapp.BuildConfig
import java.lang.reflect.Type

class SharedPreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        BuildConfig.SHARED_KEY, Context.MODE_PRIVATE
    )
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val gson = Gson()

    fun <T> saveItemList(itemList: List<T>, key: String) {
        val json = gson.toJson(itemList)
        editor.putString(key, json)
        editor.apply()
    }

    fun <T> getItemList(key: String, type: Type): List<T> {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}