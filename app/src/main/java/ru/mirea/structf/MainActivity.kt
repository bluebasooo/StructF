package ru.mirea.structf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.mirea.structf.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        setContentView(mainBinding.root)
        mainBinding.homeButton.setOnClickListener {
            Toast.makeText(applicationContext, "This home button", Toast.LENGTH_SHORT).show()
        }
    }
}