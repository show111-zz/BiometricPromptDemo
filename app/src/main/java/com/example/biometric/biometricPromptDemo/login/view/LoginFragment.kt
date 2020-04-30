package com.example.biometric.biometricPromptDemo.login.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.biometric.BiometricConstants
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.biometric.biometricPromptDemo.R
import com.example.biometric.biometricPromptDemo.login.viewmodel.LoginViewModel

class LoginFragment : Fragment(){

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var biometricManager: BiometricManager
    private lateinit var biometricPrompt: BiometricPrompt

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        initBiometric()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun initBiometric() {
        context?.let {context ->
            biometricManager = BiometricManager.from(context)
            biometricPrompt = getBiometricPromptInstance()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textLogin: TextView = view.findViewById(R.id.text_login)
        loginViewModel.text.observe(viewLifecycleOwner, Observer {
            textLogin.text = it
        })

        val imageFinger: ImageView = view.findViewById(R.id.img_finger)
        imageFinger.setOnClickListener {
            biometricPrompt.authenticate(getPromptInfoInstance())
        }
    }

    private fun getPromptInfoInstance() : BiometricPrompt.PromptInfo{
        return  BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Prompt")
            .setSubtitle("Login in to get access")
            .setDeviceCredentialAllowed(true)
            .setConfirmationRequired(true)
            .build()
    }

    private fun getBiometricPromptInstance(): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(context)

        val callback = object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                findNavController().navigate(R.id.home_navigation)

            }
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if(errorCode == BiometricConstants.ERROR_USER_CANCELED){
                    startActivity(Intent(Settings.ACTION_SETTINGS))
                }
            }
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d("hui", "Authentication failed.")
            }
        }
        return BiometricPrompt(this, executor, callback)
    }


}
