package com.example.nft.ui.notifications

import android.app.Activity.RESULT_OK
import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cloudinary.Cloudinary
import com.cloudinary.Transformation
import com.cloudinary.android.Logger.e
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

import com.example.nft.databinding.FragmentMintBinding

import okhttp3.*
import java.io.File


class MIntFragment : Fragment() {

//    var config: HashMap<String, String> = HashMap()


    private var _binding: FragmentMintBinding? = null
    lateinit var imgView:ImageView
    lateinit var btnChange:Button
    lateinit var btnUpload:Button
    lateinit var imageUri:Uri



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

        val requestBody =file.asRequestBody("image/*".toMediaTypeOrNull())
        val part =MultipartBody.Part.createFormData("profile",file.name,requestBody)

        val retrofit=Retrofit.Builder().baseUrl("https://nft-mpa0.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UploadService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
           val response= retrofit.uploadImage(part)
            e("res:",response.body().toString())
            if(response.isSuccessful){
                _binding?.btnMint?.isEnabled=true
            }

        }


    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}