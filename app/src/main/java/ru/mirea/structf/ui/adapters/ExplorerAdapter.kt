package ru.mirea.structf.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.structf.R
import ru.mirea.structf.ui.viewmodels.ExplorerViewModel
import java.util.function.BiConsumer

class ExplorerAdapter(
    private var folders: List<String>,
    private val model: ExplorerViewModel,
    private val listner: BiConsumer<View, String>
) : RecyclerView.Adapter<ExplorerAdapter.ExplorerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExplorerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_explorer, parent, false)

        return ExplorerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExplorerViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.tvFolderTitle).text = folders[position]
            setOnClickListener {
                model.goDown(folders[position])
            }
            setOnLongClickListener {
                listner.accept(it, folders[position])
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    fun setFolders(folders: List<String>) {
        this.folders = folders
    }

    inner class ExplorerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}