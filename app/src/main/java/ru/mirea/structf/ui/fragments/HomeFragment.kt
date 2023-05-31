package ru.mirea.structf.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.structf.BuildConfig
import ru.mirea.structf.R
import ru.mirea.structf.data.model.Doc
import ru.mirea.structf.data.model.Tag
import ru.mirea.structf.databinding.FragmentHomeBinding
import ru.mirea.structf.ui.MainActivity
import ru.mirea.structf.ui.viewmodels.DocViewModel
import ru.mirea.structf.ui.viewmodels.MainViewModel
import java.io.File

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        return homeBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setPopularTags(popularOnView)
//        setRecentDocs(recentOnView)

        //homeBinding.scale.findViewById<ProgressBar>(R.id.vertical_progressbar).progress = (percentOnView*100).toInt()
        //setRecentDocs(recentOnView)
        //setPopularTags(popularOnView)

        mainViewModel.apply {
            percent.observe(viewLifecycleOwner) {
                homeBinding.scale
                           .findViewById<ProgressBar>(R.id.vertical_progressbar)
                           .progress = ((it ?: 0.0)*100).toInt()
            }
            popular.observe(viewLifecycleOwner) {
                setPopularTags(it)
            }
            recent.observe(viewLifecycleOwner) {
                setRecentDocs(it)
            }
        }

//        setRecentDocs(listOf(
//            Doc(name = "aaa.pdf", path = ""),
//            Doc(name = "bbb.docx", path = ""),
//            Doc(name = "ccc.pdf", path = ""),
//            Doc(name = "ddd.pdf", path = ""),
//            Doc(name = "eee.pdf", path = ""),
//            Doc(name = "fff.pdf", path = "")
//        ))
//
//        setPopularTags(listOf(
//            Tag(endPath = "", name = "privet", color = "Green"),
//            Tag(endPath = "", name = "kak", color = "Yellow"),
//            Tag(endPath = "", name = "dela", color = "Pink")
//        ))

    }

    fun setRecentDocs(docs: List<Doc>) {
        homeBinding.recentDocs.apply {
            children.forEachIndexed { index, view ->
                if(index == docs.size) {return}
                view.findViewById<View>(R.id.recent_icon)
                    .setBackgroundResource(
                        chooseIcon(docs[index].name.substringAfterLast('.'))
                    )
                view.findViewById<TextView>(R.id.recent_text).text = docs[index].name
                view.setOnClickListener {
                    val isImage = docs[index].name.substringAfterLast(".").let {
                        it.startsWith("j") || it.startsWith("png")
                    }
                    val type = if(isImage) "image/*" else "application/*"

                    Intent(Intent.ACTION_VIEW).also {
                        it.setDataAndType(
                            FileProvider.getUriForFile(
                                context.applicationContext,
                                context.applicationContext.packageName + ".provider",
                                File(docs[index].path,docs[index].name)
                            ),
                            type
                        )
                        it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        startActivity(it)
                    }
                }
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

    fun setPopularTags(tags: List<Tag>) {
        homeBinding.popularTags.apply {
            children.forEachIndexed { index, view ->
                if(index == tags.size) {return}
                view.findViewById<ConstraintLayout>(R.id.recent_tag_back)
                    .setBackgroundResource(
                        chooseTagsColor(tags[index].color)
                    )
                view.findViewById<TextView>(R.id.recent_tag_name).text = tags[index].name
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
}