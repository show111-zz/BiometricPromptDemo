package com.example.biometric.biometricPromptDemo.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.biometric.biometricPromptDemo.R
import com.example.biometric.biometricPromptDemo.home.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = view.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val navView: BottomNavigationView = view.findViewById(R.id.nav_view)
        val navController = findNavController(this)
        navView.setupWithNavController(navController)

    }

}
