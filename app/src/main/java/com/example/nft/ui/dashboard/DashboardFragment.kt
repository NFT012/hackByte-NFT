package com.example.nft.ui.dashboard

import android.annotation.SuppressLint
import android.app.ProgressDialog
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
import com.example.nft.di.preferenceHelper
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

    private var _binding: FragmentDashboardBinding? = null
   // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var dataList : ArrayList<Nft> = ArrayList()
    lateinit var viewModal: MainViewModal
    private lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        var wid = preferenceHelper(requireContext()).wid
        e("wid",wid)
        initView()
        viewModal =
            ViewModelProvider(this)[MainViewModal::class.java]
        setObservers()
        binding?.rvUserNft?.let { rv->
            rv.layoutManager = LinearLayoutManager(requireContext())
            rv.adapter = dataAdapter(requireContext(),dataList)
        }
        viewModal.get(wid!!)


        return root
    }

    fun initView(){
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setCancelable(false)


    }

    @SuppressLint("NotifyDataSetChanged")
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
                            binding?.rvUserNft?.adapter?.notifyDataSetChanged()
                            progressDialog.dismiss()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    Resource.Status.ERROR -> {
                        progressDialog.dismiss()
                    }
                    Resource.Status.LOADING -> {
                        progressDialog.setMessage("Loading ...")
                        progressDialog.show()
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