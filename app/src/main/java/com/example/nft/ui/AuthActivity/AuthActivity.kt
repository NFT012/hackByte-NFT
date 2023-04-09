package com.example.nft.ui.AuthActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nft.databinding.ActivityAuthBinding
import com.example.nft.di.Resource
import com.example.nft.di.preferenceHelper
import com.example.nft.model.LoginRequest
import com.example.nft.ui.MainActivity
import com.example.nft.ui.MainViewModal
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.CustomInjection.inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    var binding : ActivityAuthBinding ?= null
    lateinit var viewModal : AuthViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModal =
            ViewModelProvider(this)[AuthViewModal::class.java]

        binding?.btnSignIn?.setOnClickListener {
            val wid = binding?.etWalletID?.text.toString()
            if (wid.isNotEmpty()){
                preferenceHelper(this).wid = wid
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }

}