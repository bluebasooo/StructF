package ru.mirea.structf

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.mirea.structf.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        toggle = ActionBarDrawerToggle(this, mainBinding.root, R.string.open, R.string.close)
        mainBinding.root.addDrawerListener(toggle)
        toggle.syncState()


        val mainFragment = MainFragment()
        val tagFragment = TagFragment()
        val sortFragment = SortFragment()

        setCurrentFragment(mainFragment)

        mainBinding.bottomMenu.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miHome -> setCurrentFragment(mainFragment)
                R.id.miStackButton -> setCurrentFragment(tagFragment)
            }
            true
        }


        mainBinding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.bHome -> findViewById<View?>(R.id.miHome).callOnClick()
                R.id.bStack -> findViewById<View?>(R.id.miStackButton).callOnClick()
                R.id.bSort -> setCurrentFragment(sortFragment)
            }
            true
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }
}