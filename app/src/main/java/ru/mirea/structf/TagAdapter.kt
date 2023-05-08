package ru.mirea.structf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.structf.dto.TagDto

class TagAdapter(
    private val tags: List<TagDto>
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

    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}