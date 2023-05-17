package ru.mirea.structf.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_ApplicationContextModule
import ru.mirea.structf.data.model.Doc
import ru.mirea.structf.databinding.FragmentTagBinding
import ru.mirea.structf.ui.adapters.FileAdapter
import ru.mirea.structf.ui.adapters.TagAdapter
import ru.mirea.structf.ui.viewmodels.DocViewModel
import ru.mirea.structf.ui.viewmodels.TagViewModel

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

        val docs = docViewModel.allDocs.value ?: listOf(
            Doc(
                name = "ZZZnocontentZZZ",
                folderId = 0,
                isTracked = false,
                tagId = 1
            )
        )
        val tfLayoutManager = LinearLayoutManager(activity)
        val tfAdapter = FileAdapter(docs)

        val tags = tagViewModel.allTags.value ?: listOf()

        tagBinding.rcFile.apply {
            layoutManager = tfLayoutManager
            adapter = tfAdapter
        }

        docViewModel.allDocs.observe(viewLifecycleOwner, Observer {
            tfAdapter.setDocs(it)
            tagBinding.rcFile.adapter = tfAdapter
        })

        tagBinding.rcTags.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = TagAdapter(tags)
        }

        tagBinding.tButton.setOnClickListener {
            AlertDialog.Builder(context).
            tagViewModel.insertTag()
        }
    }


}