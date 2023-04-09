package com.example.nft.ui.dashboard

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nft.R
import com.example.nft.model.IPFSFileDetail

class dataAdapter(private var context : Context, private val tokenList: List<Nft>): RecyclerView.Adapter<dataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usernft, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tokenList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = tokenList[position]

        holder.address?.text = ItemsViewModel.contractAddress
        holder.name?.text = ItemsViewModel.tokenName
        holder.id?.text = ItemsViewModel.tokenName
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val id: TextView ?= itemView.findViewById(R.id.rv_tokenID)
        val address: TextView ?= itemView.findViewById(R.id.rv_contractAddressr)
        val name : TextView ?= itemView.findViewById(R.id.rv_tokenName)
    }

}