package com.example.nft.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cloudinary.android.Logger
import com.cloudinary.android.Logger.e
import com.example.nft.UploadService
import com.example.nft.databinding.FragmentDashboardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashboardFragment : Fragment() {
    var wid="0x89a2A2bEDF626Cef45Fa39F8fEA3922Ab654Feb8"

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val retrofit= Retrofit.Builder().baseUrl("https://api.verbwire.com/v1/nft/data/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UploadService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response= retrofit.getMyNFT(wid,"mumbai")
            Log.e("getNFTs:", response.body().toString())
            val nftListDetails:ArrayList<NftDetails> ?=null

            if(response.isSuccessful){
                Log.e("getNFTs:", "hn")
//
//                val nftList: List<Nft>? =response.body()?.nfts

            }

        }



        return root
    }

}