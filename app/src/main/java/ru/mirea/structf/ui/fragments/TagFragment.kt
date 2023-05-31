package ru.mirea.structf.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.structf.data.model.Doc
import ru.mirea.structf.databinding.FragmentTagBinding
import ru.mirea.structf.ui.adapters.FileAdapter
import ru.mirea.structf.ui.adapters.TagAdapter
import ru.mirea.structf.ui.viewmodels.DocViewModel
import ru.mirea.structf.ui.viewmodels.TagViewModel
import java.io.File

@AndroidEntryPoint
class TagFragment : Fragment() {

    private lateinit var tagBinding: FragmentTagBinding
    private val docViewModel: DocViewModel by viewModels()
    private val tagViewModel: TagViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        tagBinding = FragmentTagBinding.inflate(inflater, container, false)

        return tagBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        docViewModel.getDocsByTag("UNTRACKED")

        val docs = docViewModel.getDocs().value ?: listOf()
        val dLayoutManager = LinearLayoutManager(activity)
        val dAdapter = FileAdapter(docs) { doc ->
            viewFile(doc)
        }

        val tags = tagViewModel.allTags.value ?: listOf()
        val tLayoutManager = LinearLayoutManager(activity)
        val tAdapter = TagAdapter(tags, docViewModel)


        tagBinding.rcFile.apply {
            layoutManager = dLayoutManager
            adapter = dAdapter
        }

        docViewModel.getDocs().observe(viewLifecycleOwner) {
            dAdapter.setDocs(it)
            tagBinding.rcFile.adapter = dAdapter
        }

        tagBinding.rcTags.apply {
            layoutManager = tLayoutManager
            adapter = tAdapter
        }

        tagViewModel.allTags.observe(viewLifecycleOwner) {
            tAdapter.setTags(it)
            tagBinding.rcTags.adapter = tAdapter
        }

        tagBinding.tButton.setOnClickListener {
        }
    }

    private fun viewFile(doc: Doc) {
        val isImage = doc.name.substringAfterLast(".").let {
            it.startsWith("j") || it.startsWith("png")
        }
        val type = if(isImage) "image/*" else "application/*"

        Intent(Intent.ACTION_VIEW).also {
            it.setDataAndType(
                FileProvider.getUriForFile(
                    requireContext().applicationContext,
                    requireContext().applicationContext.packageName + ".provider",
                    File(doc.path,doc.name)
                ),
                type
            )
            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(it)
        }
    }


}