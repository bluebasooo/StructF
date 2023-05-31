package ru.mirea.structf.ui.adapters

import android.content.ClipData
import android.content.ClipDescription
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.structf.R
import ru.mirea.structf.data.model.Doc
import java.util.function.Consumer

class FileAdapter(
    private var docs: List<Doc>,
    private val listner: Consumer<Doc>
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)

        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val folder = docs
        holder.itemView.apply {
            findViewById<TextView>(R.id.currentRootFolderName).text = folder[position].name

            val img = findViewById<ImageView>(R.id.iconFile)
            img.setBackgroundResource(chooseIcon(
                folder[position].name.substringAfterLast('.')
            ))

            setOnLongClickListener {
                val item = ClipData.Item("${folder[position].docId.toString()}^${folder[position].path}^${folder[position].name}^${folder[position].tagId.toString()}^${position}")
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData("toTag", mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            setOnClickListener {
                listner.accept(docs[position])
            }
        }


    }

    fun chooseIcon(typeDoc: String): Int {
        return when(typeDoc) {
            "pdf" -> R.drawable.pdf_ic
            "docx" -> R.drawable.doc_ic
            "doc" -> R.drawable.doc_ic
            "png" -> R.drawable.pic_ic
            "jpg" -> R.drawable.pic_ic
            "jpeg" -> R.drawable.pic_ic
            "xls" -> R.drawable.xls_ic
            "xlsx" -> R.drawable.xls_ic
            "rar" -> R.drawable.rar_ic
            "zip" -> R.drawable.rar_ic
            else -> 0
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