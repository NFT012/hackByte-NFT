package com.example.nft.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nft.databinding.FragmentMintBinding

class MIntFragment : Fragment() {

    private var _binding: FragmentMintBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mintViewModel =
            ViewModelProvider(this).get(MintViewModel::class.java)

        _binding = FragmentMintBinding.inflate(inflater, container, false)
        val root: View = binding.root




        return root
    }

    fun validate(): Boolean? {
        return _binding?.etName?.text?.isEmpty() == true && _binding?.etDesc?.text?.isEmpty() == true && _binding?.etWid?.text?.isEmpty() == true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}