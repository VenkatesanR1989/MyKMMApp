
package com.example.mykmmapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.GrainApi
typealias Entry = com.example.shared.Grain
class GrainListAdapter(private val api: GrainApi) :
    RecyclerView.Adapter<GrainListAdapter.Holder>() {

  private val grainList: ArrayList<Entry> = arrayListOf()

  var onClick: (Entry, Int) -> Unit = { _, _ -> }
  var handleError: (Throwable?) -> Unit = {}

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
    return LayoutInflater.from(parent.context)
        .inflate(R.layout.item_grain, parent, false)
        .let { Holder(it as ViewGroup) }
  }

  override fun getItemCount(): Int = grainList.count()

  override fun onBindViewHolder(holder: Holder, position: Int) {
    holder.bind(grainList[position])
  }

  fun updateData(list: List<Entry>) {
    grainList.clear()
    grainList.addAll(list)
    notifyDataSetChanged()
  }

  inner class Holder(private val view: ViewGroup) : RecyclerView.ViewHolder(view), View
  .OnClickListener {

    private var textView: TextView = view.findViewById(R.id.textView)
    private var imageView: ImageView = view.findViewById(R.id.imageView)

    init {
      view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
      onClick(grainList[adapterPosition], adapterPosition)
    }

    fun bind(item: Entry) {
      val isFavorite = api.isFavorite(item.id)
      textView.setCompoundDrawablesWithIntrinsicBounds(
          null,
          null,
          if (isFavorite) ContextCompat.getDrawable(view.context, android.R.drawable.star_big_on)
          else null,
          null
      )

      textView.text = item.name
      item.url?.let { imageUrl ->
        api.getImage(imageUrl, { image ->
          imageView.setImageBitmap(image)
        }, {
          handleError(it)
        })
      }
    }
  }
}