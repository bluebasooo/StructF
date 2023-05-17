package ru.mirea.structf.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.structf.R
import ru.mirea.structf.domain.Explorer

class ExplorerAdapter(
    private var exp: Explorer
) : RecyclerView.Adapter<ExplorerAdapter.ExplorerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExplorerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)

        return ExplorerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExplorerViewHolder, position: Int) {
        val folder = exp.getFilesFromCurrentFolder()
        holder.itemView.apply {
            findViewById<TextView>(R.id.tvTitle).text = folder[position]
            setOnClickListener {
                exp.down(folder[position])
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount(): Int {
        return exp.getFilesFromCurrentFolder().size
    }

    inner class ExplorerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}