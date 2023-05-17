package ru.mirea.structf.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.structf.R
import ru.mirea.structf.data.model.Doc

class FileAdapter(
    private var docs: List<Doc>
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)

        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val folder = docs
        holder.itemView.apply {
                findViewById<TextView>(R.id.tvTitle).text = folder[position].name
        }

    }

    fun setDocs(docs: List<Doc>) {
        this.docs = docs
    }

    override fun getItemCount(): Int {
        return docs.size
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}