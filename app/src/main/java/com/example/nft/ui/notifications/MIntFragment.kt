package com.example.nft.ui.notifications

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cloudinary.android.Logger.e
import com.example.nft.UploadService
import com.example.nft.databinding.FragmentMintBinding
import com.example.nft.ui.MintModel
import com.example.nft.ui.NftX
import com.example.nft.ui.nft
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit


class MIntFragment : Fragment() {

//    var config: HashMap<String, String> = HashMap()


    private var _binding: FragmentMintBinding? = null
    lateinit var imgView: ImageView
    lateinit var btnChange: Button
    lateinit var btnUpload: Button
    lateinit var imageUri: Uri

    lateinit var cloudURL:String



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var contract =registerForActivityResult(ActivityResultContracts.GetContent()){
        imgView.setImageURI(it)
        imageUri= it!!
        _binding?.btnIpfs?.isEnabled=true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMintBinding.inflate(inflater, container, false)
        val root: View = binding.root


        setup()

        _binding?.btnMint?.setOnClickListener {
            var nameNFT =_binding?.etName?.text?.toString()
            var descNFT =_binding?.etDesc?.text?.toString()
            var wid=_binding?.etWid?.text?.toString()
            var chain =_binding?.etChain?.text?.toString()

            var mint =MintModel(wid!!,cloudURL,nameNFT!!,chain!!,descNFT!!,"imageNFT")
            var itemNFT= nft(NftX(chain, data = "data",descNFT, cloudURL,nameNFT,wid))

            e("jaane wala data:",itemNFT.toString())


            val retrofit=Retrofit.Builder().baseUrl("https://nft-mpa0.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UploadService::class.java)

            CoroutineScope(Dispatchers.IO).launch {
                val response= retrofit.createNFT(itemNFT)
                e("res:",response.body().toString())

                if(response.isSuccessful){

                }

            }


        }

        return root
    }

    private fun setup() {
        imgView= _binding?.ivUpload!!
        btnChange=_binding?.btnIpfs!!
        imgView.setOnClickListener {
            contract.launch("image/*")
        }
        btnChange.setOnClickListener {
            upload()
        }
    }

    private fun upload() {

        val filesDir = activity?.applicationContext?.filesDir
        val file=File(filesDir,"image.png")
        val inputStream=activity?.contentResolver?.openInputStream(imageUri)
        val outputStream =FileOutputStream(file)
        inputStream!!.copyTo(outputStream)

//        val requestBody =file.asRequestBody("image/*".toMediaTypeOrNull())
        val requestBody =RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

        val part =MultipartBody.Part.createFormData("image",file.name,requestBody)

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS).build()


        val retrofit=Retrofit.Builder().baseUrl("https://nft-mpa0.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(UploadService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
           val response= retrofit.uploadImage(part)
            e("res:",response.body().toString())

            if(response.isSuccessful){
                cloudURL= response.body()?.url.toString()
//                _binding?.btnMint?.isEnabled=true
            }

        }


    }





}


