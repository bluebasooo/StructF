package ru.mirea.structf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.structf.dto.DocDto

class FileAdapter(
    private var files: MutableList<DocDto>
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)

        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tvTitle).text = files[position].name
    }

    override fun getItemCount(): Int {
        return files.size
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}