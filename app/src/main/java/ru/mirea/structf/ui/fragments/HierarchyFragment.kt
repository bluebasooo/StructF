package ru.mirea.structf.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.structf.R
import ru.mirea.structf.data.model.Tag
import ru.mirea.structf.databinding.FragmentHierarchyBinding
import ru.mirea.structf.ui.adapters.FolderAdapter
import ru.mirea.structf.ui.viewmodels.ExplorerViewModel

@AndroidEntryPoint
class HierarchyFragment: Fragment() {
    private lateinit var hierarchyBinding: FragmentHierarchyBinding
    private val hierarchyViewModel: ExplorerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        hierarchyBinding = FragmentHierarchyBinding.inflate(inflater, container, false)

        return hierarchyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val folders = hierarchyViewModel.getFolders().value ?: listOf()

        val fLayoutManager = LinearLayoutManager(activity)
        val fAdapter = FolderAdapter(folders, hierarchyViewModel, activity)

        hierarchyBinding.treeSubfolder.apply {
            layoutManager = fLayoutManager
            adapter = fAdapter
        }

        hierarchyViewModel.getFolders().observe(viewLifecycleOwner) {
            hierarchyBinding.currentAbsPath.text = hierarchyViewModel.downPath()
            hierarchyBinding.currentRootFolderName.text = hierarchyViewModel.getName()
            fAdapter.setFolders(it)
            hierarchyBinding.treeSubfolder.adapter = fAdapter
        }

        hierarchyBinding.coreFolder.setOnClickListener {
            hierarchyViewModel.goUp()
        }

        hierarchyBinding.hierarchyPath.setOnLongClickListener {
            activity.let {
                val builder = AlertDialog.Builder(it)
                val inflater = requireActivity().layoutInflater
                val dialog = inflater.inflate(R.layout.create_tag_dialog, null)
                builder.setView(dialog)
                val win = builder.create()
                var colorTag = "Blue"
                dialog.apply {
                    findViewById<Button>(R.id.create_tag_button).setOnClickListener {
                        val name = dialog.findViewById<TextInputEditText>(R.id.enter_name_tag).text.toString()
                        if(name.isNotBlank()) {
                            hierarchyViewModel.createHierarchyAboutTag(
                                Tag(
                                    name = name,
                                    endPath = hierarchyViewModel.downPath(),
                                    color = colorTag,
                                )
                            )
                            win.dismiss()
                        }
                    }
                    val tagView = findViewById<View>(R.id.tag_view)
                    findViewById<View>(R.id.clrBlue).setOnClickListener {
                        tagView.backgroundTintList = it.backgroundTintList
                        colorTag = "Blue"
                    }
                    findViewById<View>(R.id.clrYellow).setOnClickListener {
                        tagView.backgroundTintList = it.backgroundTintList
                        colorTag = "Yellow"
                    }
                    findViewById<View>(R.id.clrGreen).setOnClickListener {
                        tagView.backgroundTintList = it.backgroundTintList
                        colorTag = "Green"
                    }
                    findViewById<View>(R.id.clrPink).setOnClickListener {
                        tagView.backgroundTintList = it.backgroundTintList
                        colorTag = "Pink"
                    }
                    findViewById<View>(R.id.clrPurple).setOnClickListener {
                        tagView.backgroundTintList = it.backgroundTintList
                        colorTag = "Purple"
                    }
                }
                win.show()
            }
            true
        }

        hierarchyBinding.plusFolder.setOnClickListener {
                activity.let {
                    val builder = AlertDialog.Builder(it)
                    val inflater = requireActivity().layoutInflater
                    val dialog = inflater.inflate(R.layout.creating_folder, null)
                    builder.setView(dialog)
                    val win = builder.create()
                    dialog.apply {
                        findViewById<Button>(R.id.materialButton2).setOnClickListener {
                            val name = dialog.findViewById<TextInputEditText>(R.id.folder_name22).text.toString()
                            if(name.isNotBlank()) {
                                hierarchyViewModel.createFolder(name)
                                win.dismiss()
                            }
                        }

                    }
                    win.show()
                }
        }
    }
}