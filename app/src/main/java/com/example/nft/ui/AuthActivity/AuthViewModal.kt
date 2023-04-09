package com.example.nft.ui.AuthActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nft.di.Resource
import com.example.nft.model.LoginRequest
import com.example.nft.model.LoginResponse
import com.example.nft.model.NftModel
import com.example.nft.repository.nftRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModal @Inject constructor(private val nftRepo: nftRepo) : ViewModel() {

    //Get All NFT
    private val performLogin = MutableLiveData<Resource<LoginResponse>>()
    val performLoginStatus: LiveData<Resource<LoginResponse>>
        get() = performLogin

    fun LoginUser(loginRequest: LoginRequest) {
        viewModelScope.launch {
            try {
                performLogin.value = Resource.loading()
                val response = nftRepo.LoginUser(loginRequest)
                performLogin.value = Resource.success(response.body()!!)
            } catch (e: Exception) {
                println("login failed ${e.message}")
                performLogin.value = Resource.error(e)
            }
        }
    }

}