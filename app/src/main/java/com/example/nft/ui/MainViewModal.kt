package com.example.nft.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nft.di.Resource
import com.example.nft.di.nftAPI
import com.example.nft.model.NftModel
import com.example.nft.repository.nftRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Error
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class MainViewModal @Inject constructor(private val nftRepo: nftRepo) : ViewModel() {

    //Get All NFT
    private val performGetNft = MutableLiveData<Resource<NftModel>>()
    val performGetNftStatus: LiveData<Resource<NftModel>>
        get() = performGetNft

    fun getAllNft() {
        viewModelScope.launch {
            try {
                performGetNft.value = Resource.loading()
                val response = nftRepo.getAllNFT()
                performGetNft.value = Resource.success(response.body()!!)
            } catch (e: Exception) {
                println("login failed ${e.message}")
                performGetNft.value = Resource.error(e)
            }
        }
    }

}