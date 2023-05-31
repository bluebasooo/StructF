package ru.mirea.structf.ui.adapters

import android.content.ClipDescription
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.structf.R
import ru.mirea.structf.data.model.Doc
import ru.mirea.structf.data.model.Tag
import ru.mirea.structf.ui.viewmodels.DocViewModel

class TagAdapter(
    private var tags: List<Tag>,
    private val model: DocViewModel
) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag, parent, false)

        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.tvTag).text = tags[position].name
            val img = findViewById<ConstraintLayout>(R.id.tagBackground)

            img.setBackgroundResource(chooseTagsColor(
                tags[position].color
            ))

            setOnClickListener {
                model.getDocsByTag(tags[position].name)
            }
            setOnDragListener { view, dragEvent ->
                when(dragEvent.action) {
                    DragEvent.ACTION_DRAG_STARTED -> {
                        dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                    }
                    DragEvent.ACTION_DRAG_ENTERED -> {
                        view.invalidate()
                        true
                    }
                    DragEvent.ACTION_DRAG_LOCATION -> true
                    DragEvent.ACTION_DRAG_EXITED -> {
                        view.invalidate()
                        true
                    }
                    DragEvent.ACTION_DROP -> {
                        val item = dragEvent.clipData.getItemAt(0)
                        val dragData = item.text.toString()
                        val data = dragData.split("^")
                        val docId = data[0].toLong()
                        val tagId = data[3].toLong()
                        if(tags[position].name == "CLOUD") {
                            model.cloudUpload(
                                Doc(path = data[1], name = data[2], tagId = 2)
                            )
                        } else {
                            model.moveDoc(docId,tags[position].tagId ,data[1], tags[position].endPath, data[2] )
                        }

                        model.getDocsByTagId(tagId)
                        view.invalidate()

                        val v = dragEvent.localState as View
                        val owner = v.parent as RecyclerView
                        owner.adapter!!.notifyItemRemoved(data[4].toInt())

                        true
                    }
                    DragEvent.ACTION_DRAG_ENDED -> {
                        view.invalidate()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    fun chooseTagsColor(color: String): Int {
        return when(color) {
            "Blue" -> R.drawable.g_blue
            "Yellow" -> R.drawable.g_yellow
            "Green" -> R.drawable.g_green
            "Pink" -> R.drawable.g_pink
            "Purple" -> R.drawable.g_purple
            else -> 0
        }
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun setTags(tags: List<Tag>) {
        this.tags = tags
    }

    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}