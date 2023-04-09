package com.example.nft.model

import java.io.Serializable

data class NftModel(
    val ipfs_upload_details: IpfsUploadDetails
) : Serializable