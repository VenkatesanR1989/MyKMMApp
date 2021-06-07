package com.example.shared

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class GrainApi(private val context: Controller) {

  private val apiUrl = "https://gist.githubusercontent.com/jblorenzo/f8b2777c217e6a77694d74e44ed6b66b/raw/0dc3e572a44b7fef0d611da32c74b187b189664a/gistfile1.txt"

  fun getGrainList(success: (List<Grain>) -> Unit, failure: (Throwable?) -> Unit) {
    GlobalScope.launch(ApplicationDispatcher) {
      try {
        val url = apiUrl
        val json = HttpClient().get<String>(url)
        Json.decodeFromString(GrainList.serializer(), json)
            .entries
            .also(success)
      } catch (ex: Exception) {
        failure(ex)
      }
    }
  }

  fun getImage(url: String, success: (Image?) -> Unit, failure: (Throwable?) -> Unit) {
    GlobalScope.launch(ApplicationDispatcher) {
      try {
        HttpClient().get<ByteArray>(url)
            .toNativeImage()
            .also(success)
      } catch (ex: Exception) {
        failure(ex)
      }
    }
  }

  fun isFavorite(id: Int): Boolean {
    return context.getBool("grain_$id")
  }

  fun setFavorite(id: Int, value: Boolean) {
    context.setBool("grain_$id", value)
  }
}