package com.example.nft.model

import java.io.Serializable

data class NftModel(
    val data: ArrayList<IPFSFileDetail>
) : Serializable