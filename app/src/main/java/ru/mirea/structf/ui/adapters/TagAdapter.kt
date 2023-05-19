package ru.mirea.structf.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.structf.R
import ru.mirea.structf.data.model.Doc
import ru.mirea.structf.data.model.Tag

class TagAdapter(
    private var tags: List<Tag>
) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag, parent, false)

        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tvTag).text = tags[position].name
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun setTags(tags: List<Tag>) {
        this.tags = tags
    }

    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}