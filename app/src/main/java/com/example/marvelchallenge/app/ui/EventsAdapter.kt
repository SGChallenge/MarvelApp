package com.example.marvelchallenge.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelchallenge.R
import com.example.marvelchallenge.data.models.ExpandableEvent
import com.example.marvelchallenge.databinding.ListItemEventsBinding
import com.example.marvelchallenge.utils.SQUARE_LARGE
import com.example.marvelchallenge.utils.formatDate
import com.example.marvelchallenge.utils.loadFromUrl

class EventsAdapter(
    private val listener: SetIsExpandedEventCallBack
) : ListAdapter<ExpandableEvent, EventsAdapter.ViewHolder>(ExpandableEventDiffCallBack) {

    interface SetIsExpandedEventCallBack {
        fun setIsExpandedEvent(id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListItemEventsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        if (item.comics?.items.isNullOrEmpty()) {
            holder.itemView.setOnClickListener(null)
        } else {
            holder.itemView.setOnClickListener {
                listener.setIsExpandedEvent(item.id)
            }
        }
    }

    class ViewHolder(private val binding: ListItemEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val comicLayoutList = listOf(
            binding.lytComic1,
            binding.lytComic2,
            binding.lytComic3,
            binding.lytComic4
        )

        fun bind(event: ExpandableEvent) {
            with(event) {
                binding.imgEventThumbnail.loadFromUrl(SQUARE_LARGE, thumbnail)
                binding.txtEventName.text = title
                binding.txtStartDate.text = start?.formatDate()
                binding.txtEndDate.text = end?.formatDate()
                binding.imgArrow.rotationX = if (isExpanded) 180f else 0f
                binding.lytComics.isVisible = isExpanded

                val items = comics?.items

                binding.imgArrow.isVisible = items.isNullOrEmpty() == false
                binding.txtComicsToDiscuss.isVisible = items.isNullOrEmpty() == false && isExpanded

                items?.take(4)?.forEachIndexed { index, comicSummary ->
                    val name = comicSummary.name
                    comicLayoutList[index].apply {
                        visibility = View.VISIBLE
                        findViewById<TextView>(R.id.txt_comic_title).text = name
                        findViewById<TextView>(R.id.txt_year).text =
                            name.substring(name.indexOf("(") + 1, name.indexOf(")"))
                    }
                }
            }
        }
    }
}

object ExpandableEventDiffCallBack : DiffUtil.ItemCallback<ExpandableEvent>() {
    override fun areItemsTheSame(oldItem: ExpandableEvent, newItem: ExpandableEvent): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ExpandableEvent, newItem: ExpandableEvent): Boolean =
        oldItem == newItem
}
