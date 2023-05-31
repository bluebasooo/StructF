package ru.mirea.structf.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.structf.R
import ru.mirea.structf.databinding.FragmentExplorerBinding
import ru.mirea.structf.ui.adapters.ExplorerAdapter
import ru.mirea.structf.ui.viewmodels.ExplorerViewModel

@AndroidEntryPoint
class ExplorerFragment : Fragment() {
    private lateinit var explorerBinding: FragmentExplorerBinding
    private val explorerViewModel: ExplorerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        explorerBinding = FragmentExplorerBinding.inflate(inflater, container, false)

        return explorerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val folders = explorerViewModel.getFolders().value ?: listOf()

        val navController = findNavController()
        val explorerAdapter = ExplorerAdapter(folders, explorerViewModel) { view, folderName ->
            explorerViewModel.goDown(folderName)
            navController.navigate(R.id.hierarchyFragment)
            true
        }

        explorerBinding.innerFolders.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = explorerAdapter
        }

        explorerBinding.cornerFolderName.apply {
            setOnClickListener {
                explorerViewModel.goUp()
            }
            setOnLongClickListener {
                explorerViewModel.goStart()
                true
            }
        }

        explorerViewModel.getFolders().observe(viewLifecycleOwner) {
            explorerBinding.cornerFolderName.text = explorerViewModel.getName()
            explorerAdapter.setFolders(it)
            explorerBinding.innerFolders.adapter = explorerAdapter
        }

    }
}

