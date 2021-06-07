package com.example.mykmmapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.GrainApi
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MultiGrainActivity : AppCompatActivity(), CoroutineScope {

  private lateinit var job: Job
  private lateinit var api: GrainApi
  private lateinit var list: RecyclerView
  private lateinit var rootView: View

  override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Main

  private lateinit var grainAdapter: GrainListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_multi_grain)

    job = Job()
    api = GrainApi(this)
    list = findViewById(R.id.list)
    rootView = findViewById(R.id.root_view)
    grainAdapter = GrainListAdapter(api)

    setupRecyclerView()

    loadList()
  }

  private fun setupRecyclerView() {
    grainAdapter.onClick = { item, position ->
      toggleFavorite(item.id)
      grainAdapter.notifyItemChanged(position)
      list.scrollBy(0, 0)
    }
    grainAdapter.handleError = ::handleError

    list.apply {
      layoutManager = LinearLayoutManager(this@MultiGrainActivity)
      adapter = grainAdapter
      itemAnimator = null
      setHasFixedSize(true)
      addItemDecoration(DividerItemDecoration(this@MultiGrainActivity, LinearLayoutManager
          .VERTICAL))
    }
  }

  private fun loadList() {
    api.getGrainList(
        success = { launch(Main) { grainAdapter.updateData(it) } },
        failure = ::handleError
    )
  }

  private fun toggleFavorite(id: Int) {
    val isFavorite = api.isFavorite(id)
    api.setFavorite(id, !isFavorite)
  }

  private fun handleError(ex: Throwable?) {
    ex?.printStackTrace()
    launch(Main) {
      val msg = ex?.message ?: "Unknown error"
      Snackbar.make(rootView, msg, Snackbar.LENGTH_INDEFINITE)
          .setAction("Retry") { loadList() }
          .show()
    }
  }

  override fun onDestroy() {
    job.cancel()
    super.onDestroy()
  }
}
