package com.example.nft.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nft.UploadService
import com.example.nft.di.Resource
import com.example.nft.di.nftAPI
import com.example.nft.model.NftModel
import com.example.nft.repository.nftRepo
import com.example.nft.ui.dashboard.Nft
import com.example.nft.ui.dashboard.dataAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    private val datalistget = MutableLiveData<Resource<ArrayList<Nft>>>()
    val datalistgetStatus: LiveData<Resource<ArrayList<Nft>>>
        get() = datalistget

    fun get(wid : String){
        viewModelScope.launch {
            try {
                val retrofit= Retrofit.Builder().baseUrl("https://api.verbwire.com/v1/nft/data/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(UploadService::class.java)

                val response = retrofit.getMyNFT(wid, "mumbai")
                Log.e("getNFTs:", response.body().toString())

                if (response.isSuccessful) {
                    Log.e("getNFTs:", "hn")
                    val dataList: ArrayList<Nft> = ArrayList()
                    response.body()?.nfts?.let { dataList.addAll(it) }
                    datalistget.value = Resource.success(dataList)
                }
            }catch (e:Exception){
                Log.e("baga",e.toString())
            }
        }
    }

}