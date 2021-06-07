package com.example.shared

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

actual typealias Controller = Activity

actual fun Controller.getBool(key: String): Boolean {
  val prefs: SharedPreferences = this.getSharedPreferences("", MODE_PRIVATE)
  return prefs.getBoolean(key, false)
}

actual fun Controller.setBool(key: String, value: Boolean) {
  val prefs: SharedPreferences = this.getSharedPreferences("", MODE_PRIVATE)
  val editor = prefs.edit()
  editor.putBoolean(key, value)
  editor.apply()
}