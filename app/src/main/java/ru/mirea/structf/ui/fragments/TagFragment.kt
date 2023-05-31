package ru.mirea.structf.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
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

        docViewModel.getDocsByTag("UNTRACKED")

        val docs = docViewModel.getDocs().value ?: listOf()
        val dLayoutManager = LinearLayoutManager(activity)
        val dAdapter = FileAdapter(docs)

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




}