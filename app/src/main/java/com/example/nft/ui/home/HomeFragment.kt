package com.example.nft.ui.home

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nft.ui.MainViewModal
import com.example.nft.databinding.FragmentHomeBinding
import com.example.nft.di.Resource
import com.example.nft.model.IPFSFileDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var progressDialog: ProgressDialog
    private var tokenList : ArrayList<IPFSFileDetail> = ArrayList()


    lateinit var viewModal: MainViewModal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModal =
            ViewModelProvider(this)[MainViewModal::class.java]
        initView()
        setObservers()
        viewModal.getAllNft()
        return root
    }

    fun initView(){
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setCancelable(false)

        binding?.rvBlogHome?.let { rv->
            rv.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
            rv.adapter = tokenAdapter(requireContext(),tokenList)
        }
    }


    fun setObservers(){
        //SIGN UP
        viewModal.performGetNftStatus.observe(requireActivity(), Observer { resource ->
            if (resource != null) {
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        if (resource.data != null) {
                            val NftData = resource.data

                            Log.e(
                                "NFT ka",
                                " Data - ${resource.data}"
                            )
                            Toast.makeText(requireContext(), "Nft Ka Data - ${resource.data}", Toast.LENGTH_LONG).show()
                            tokenList.addAll(resource.data.ipfs_upload_details.IPFS_file_details)
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
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
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