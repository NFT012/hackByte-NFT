package com.example.nft.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cloudinary.android.Logger
import com.cloudinary.android.Logger.e
import com.example.nft.UploadService
import com.example.nft.databinding.FragmentDashboardBinding
import com.example.nft.di.Resource
import com.example.nft.ui.MainViewModal
import com.example.nft.ui.home.tokenAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    var wid="0x89a2A2bEDF626Cef45Fa39F8fEA3922Ab654Feb8"

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var dataList : ArrayList<Nft> = ArrayList()
    lateinit var viewModal: MainViewModal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModal =
            ViewModelProvider(this)[MainViewModal::class.java]
        setObservers()
        viewModal.get(wid)


        return root
    }

    fun setObservers(){
        //SIGN UP
        viewModal.datalistgetStatus.observe(requireActivity(), Observer { resource ->
            if (resource != null) {
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        if (resource.data != null) {
                            val NftData = resource.data

                            Log.e(
                                "NFT ka",
                                " Data - ${resource.data}"
                            )
                            dataList.addAll(resource.data)
                            Toast.makeText(requireContext(), dataList.toString(), Toast.LENGTH_SHORT).show()
                            binding?.rvUserNft?.let { rv->
                                rv.layoutManager = LinearLayoutManager(requireContext())
                                rv.adapter = dataAdapter(requireContext(),dataList)
                            }

                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    Resource.Status.ERROR -> {
                    }
                    Resource.Status.LOADING -> {
                    }
                    else -> {}
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}