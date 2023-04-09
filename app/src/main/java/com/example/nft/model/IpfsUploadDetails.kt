package com.example.nft.model

data class IpfsUploadDetails(
    val IPFS_file_details: List<IPFSFileDetail>,
    val number_of_uploaded_files: Int,
    val user: String
)