package ru.mirea.structf.ui.fragments

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.mirea.structf.databinding.FragmentTagBinding
import ru.mirea.structf.domain.Explorer
import ru.mirea.structf.dto.DocDto
import ru.mirea.structf.dto.TagDto
import ru.mirea.structf.ui.adapters.FileAdapter
import ru.mirea.structf.ui.adapters.TagAdapter

class TagFragment : Fragment() {

    private lateinit var tagBinding: FragmentTagBinding

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

        val tags = mutableListOf(
            TagDto("UNIV", "red"),
            TagDto("HZ", "blue"),
            TagDto("HZ", "blue"),
            TagDto("HZ", "blue"),
            TagDto("HZ", "blue"),
            TagDto("HZ", "blue"),
            TagDto("HZ", "blue")
        )

        val exp = Explorer(Environment.getExternalStorageDirectory().toString())

        tagBinding.rcFile.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = FileAdapter(exp)
        }

        tagBinding.fButton.setOnClickListener {
            exp.up()
            tagBinding.rcFile.adapter!!.notifyDataSetChanged()
        }

        tagBinding.tags.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = TagAdapter(tags)
        }
    }


}