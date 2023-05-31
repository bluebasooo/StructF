package ru.mirea.structf.ui.fragments.auth

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.mirea.structf.R
import ru.mirea.structf.databinding.FragmentAuthBinding
import ru.mirea.structf.ui.fragments.MainFragment
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment: Fragment() {

    @Inject lateinit var auth: FirebaseAuth
    private lateinit var authFragmentBinding: FragmentAuthBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        authFragmentBinding = FragmentAuthBinding.inflate(inflater, container, false)

        return authFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = this.findNavController()

        authFragmentBinding.apply {
            logInButton.setOnClickListener {
                authUser()
            }

            logPassword.findViewById<TextView>(R.id.sign_in_button).setOnClickListener {
                navController.navigate(R.id.registrationFragment)
            }
        }

    }

    private fun authUser() {
        val email = authFragmentBinding.logEnterEmail.text.toString()
        val password = authFragmentBinding.enterLogPassword.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInState()
                    }
                } catch(e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }

    private fun checkLoggedInState() {
        if(auth.currentUser == null) {
            Toast.makeText(context, "LogIn failed", Toast.LENGTH_SHORT).show()
        } else {
            //navController.navigate(R.id.action_authFragment_to_mainFragment2, null, NavOptions.Builder().setPopUpTo(R.id.mainFragment2, true).build())
            navController.navigate(R.id.action_authFragment_to_mainFragment2)
        }
    }
}