package com.example.biometric.biometricPromptDemo.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LoginViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Login Page"
    }
    var text: LiveData<String> = _text

}
