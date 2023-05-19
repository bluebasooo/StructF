package ru.mirea.structf.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mirea.structf.databinding.ActivityAuthorizationBinding

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var authBinding: ActivityAuthorizationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authBinding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(authBinding.root)


    }
}