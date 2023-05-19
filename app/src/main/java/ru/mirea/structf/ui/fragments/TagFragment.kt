package ru.mirea.structf.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.structf.R
import ru.mirea.structf.data.model.Doc
import ru.mirea.structf.data.model.Tag
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
        val dLayoutManager = LinearLayoutManager(activity)
        val dAdapter = FileAdapter(docs)

        val tags = tagViewModel.allTags.value ?: listOf()
        val tLayoutManager = LinearLayoutManager(activity)
        val tAdapter = TagAdapter(tags)


        tagBinding.rcFile.apply {
            layoutManager = dLayoutManager
            adapter = dAdapter
        }

        docViewModel.allDocs.observe(viewLifecycleOwner) {
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
            activity.let {
                val builder = AlertDialog.Builder(it)
                val inflater = requireActivity().layoutInflater
                val dialog = inflater.inflate(R.layout.create_tag_dialog, null)
                dialog.findViewById<Button>(R.id.create_button).setOnClickListener {
                    val name = dialog.findViewById<EditText>(R.id.setTagName).text.toString()
                    if(name.isNotBlank()) {
                        tagViewModel.insertTag(
                            Tag(
                                name = name,
                                id = 0,
                                color = "White"
                            )
                        )
                    }
                }

                builder.setView(dialog)
                builder.create().show()
            }
        }
    }




}