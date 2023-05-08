package ru.mirea.structf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.mirea.structf.databinding.FragmentTagBinding
import ru.mirea.structf.dto.DocDto
import ru.mirea.structf.dto.TagDto

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

        val files = mutableListOf(
            DocDto("init.pdf"),
            DocDto("to.doc"),
            DocDto("DAMN.exe") ,
            DocDto("init.pdf"),
            DocDto("to.doc"),
            DocDto("DAMN.exe"),
            DocDto("init.pdf"),
            DocDto("to.doc"),
            DocDto("DAMN.exe"),
            DocDto("init.pdf"),
            DocDto("to.doc"),
            DocDto("DAMN.exe")
        )

        val tags = mutableListOf(
            TagDto("UNIV", "red"),
            TagDto("HZ", "blue"),
            TagDto("HZ", "blue"),
            TagDto("HZ", "blue"),
            TagDto("HZ", "blue"),
            TagDto("HZ", "blue"),
            TagDto("HZ", "blue")
        )

        tagBinding.file.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = FileAdapter(files)
        }

        tagBinding.tags.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = TagAdapter(tags)
        }
    }


}