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
import com.example.nft.model.LoginRequest
import com.example.nft.ui.MainActivity
import com.example.nft.ui.MainViewModal
import dagger.hilt.android.AndroidEntryPoint

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
            val username = binding?.etUsername?.text.toString()

            if (wid.isNotEmpty() && username.isNotEmpty()){
                viewModal.LoginUser(LoginRequest(wid,username))
            }
        }
    }

    fun setObservers(){
        //SIGN UP
        viewModal.performLoginStatus.observe(this, Observer { resource ->
            if (resource != null) {
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        if (resource.data != null) {
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()

                        } else {
                            Toast.makeText(
                                this,
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    Resource.Status.ERROR -> {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.LOADING -> {
                    }
                    else -> {}
                }
            }
        })
    }
}