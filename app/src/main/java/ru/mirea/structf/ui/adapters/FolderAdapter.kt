package ru.mirea.structf.ui.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ru.mirea.structf.R
import ru.mirea.structf.ui.viewmodels.ExplorerViewModel

class FolderAdapter(
    private var folders: List<String>,
    private val model: ExplorerViewModel,
    private val activity: FragmentActivity?
): RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_folder, parent, false)

        return FolderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.fldr_ttl).text = folders[position]
            setOnClickListener {
                model.goDown(folders[position])
            }

            setOnLongClickListener {
                activity.let {
                    val builder = AlertDialog.Builder(it)
                    val inflater = it!!.layoutInflater
                    val dialog = inflater.inflate(R.layout.creating_folder, null)
                    dialog.findViewById<TextInputEditText>(R.id.folder_name22).setText(folders[position])
                    builder.setView(dialog)
                    val win = builder.create()
                    dialog.apply {
                        findViewById<Button>(R.id.materialButton2).setOnClickListener {
                            val name = dialog.findViewById<TextInputEditText>(R.id.folder_name22).text.toString()
                            if(name.isNotBlank()) {
                                model.renameFolder(folders[position], name)
                                win.dismiss()
                            }
                        }

                    }
                    win.show()
                }
                true
            }
        }
    }

    fun setFolders(folders: List<String>) {
        this.folders = folders
    }

    inner class FolderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}