package ru.mirea.structf.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.structf.R
import ru.mirea.structf.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : Fragment() {

    //private val constants = getSharedPreferences("properties", Context.MODE_PRIVATE)

    private lateinit var mainFragmentBinding: FragmentMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mainFragmentBinding = FragmentMainBinding.inflate(inflater, container, false)

        return mainFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toggle = ActionBarDrawerToggle(activity, mainFragmentBinding.root, R.string.open, R.string.close)
        mainFragmentBinding.root.addDrawerListener(toggle)
        toggle.syncState()




        val navHostFragment = childFragmentManager.findFragmentById(R.id.main_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        mainFragmentBinding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.bHome -> {
                    navController.navigate(R.id.mainFragment)
                    mainFragmentBinding.root.closeDrawers()
                    true
                }
                R.id.bStack -> {
                    navController.navigate(R.id.tagFragment)
                    mainFragmentBinding.root.closeDrawers()
                    true
                }
                R.id.bExplorer -> {
                    navController.navigate(R.id.explorerFragment)
                    mainFragmentBinding.root.closeDrawers()
                    true
                }
                else -> false
            }
        }

        mainFragmentBinding.bottomMenu.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}