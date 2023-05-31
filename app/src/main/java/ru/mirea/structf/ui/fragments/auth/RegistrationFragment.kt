package ru.mirea.structf.ui.fragments.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.mirea.structf.R
import ru.mirea.structf.databinding.FragmentRegistrationBinding
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val appInfo = activity?.getSharedPreferences("appInfo", Context.MODE_PRIVATE)
    private lateinit var regFragmentBinding: FragmentRegistrationBinding
    @Inject lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        regFragmentBinding = FragmentRegistrationBinding.inflate(inflater, container, false)

        return regFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = this.findNavController()

        regFragmentBinding.apply {
            regInButton.setOnClickListener {
                regUser()
            }
            signUpButton.setOnClickListener {
                navController.navigate(R.id.authFragment)
            }
        }

    }

    private fun regUser() {
        val userName = regFragmentBinding.regEnterUsername.toString()
        appInfo?.edit()?.putString("user_name", userName)?.apply()
        val email = regFragmentBinding.regEnterEmail.text.toString()
        val password = regFragmentBinding.regEnterPassword.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
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
            Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
        } else {
            navController.navigate(R.id.action_authFragment_to_registrationFragment)
        }
    }
}