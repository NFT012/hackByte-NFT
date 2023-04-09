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
import com.cloudinary.android.Logger.e
import com.example.nft.UploadService
import com.example.nft.databinding.FragmentMintBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class MIntFragment : Fragment() {
    private var _binding: FragmentMintBinding? = null
    lateinit var imgView: ImageView
    lateinit var btnChange: Button
    lateinit var btnUpload: Button
    lateinit var imageUri: Uri

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var contract = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imgView.setImageURI(uri)
        if (uri != null) {
            imageUri = uri
        }
        _binding?.btnIpfs?.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMintBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root
    }

    private fun setup() {
        imgView = _binding?.ivUpload!!
        btnChange = _binding?.btnIpfs!!
        imgView.setOnClickListener {
            contract.launch("image/*")
        }
        btnChange.setOnClickListener {
            upload()
        }
    }

    private fun upload() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val filesDir = activity?.applicationContext?.filesDir
                val file = File(filesDir, "image.png")
                val inputStream = activity?.contentResolver?.openInputStream(imageUri)
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                outputStream.close()
                inputStream?.close()

                val requestBod = file.asRequestBody("image/*".toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("profile", file.name, requestBod)

                // Build the request body
                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.name, requestBod)
                    .build()

                // Make the network request
                val request = Request.Builder()
                    .url("https://nft-mpa0.onrender.com/")
                    .post(requestBody)
                    .build()

                val client = OkHttpClient()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    e("Successful" , response.toString())
//                    Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_LONG).show()
                } else {
                    e("Failed" , "Something went Wrong ${response.body} and ksjkd  ${response.message}")
//                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
//                        .show()
                }
            } catch (e: Exception) {
                e("Error" , e.toString())
//                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

}

//        val retrofit= Retrofit.Builder().baseUrl("https://nft-mpa0.onrender.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(UploadService::class.java)
//
//        CoroutineScope(Dispatchers.IO).launch {
//           val response= retrofit.uploadImage(part)
//            e("res:",response.body().toString())
//            if(response.isSuccessful){
//                _binding?.btnMint
