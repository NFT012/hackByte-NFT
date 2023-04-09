package com.example.nft.ui.home

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nft.R
import com.example.nft.model.IPFSFileDetail
import com.example.nft.model.NftModel

class tokenAdapter(private var context : Context, private val tokenList: List<IPFSFileDetail>): RecyclerView.Adapter<tokenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.token_layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tokenList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = tokenList[position]

        holder.title.text = ItemsViewModel.user
        Glide.with(context).load(ItemsViewModel.ipfsUrl)
            .placeholder(R.drawable.ic_nft_placeholder)
            .into(holder.image)
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.TokenName)
        val image: ImageView = itemView.findViewById(R.id.tokenImage)
    }

}