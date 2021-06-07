package com.example.mykmmapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  fun openGrains(v: View?) {
    val intent = Intent(this, MultiGrainActivity::class.java)
    startActivity(intent)
  }
}
