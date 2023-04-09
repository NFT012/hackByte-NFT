package com.example.nft.model

data class IPFSFileDetail(
    val apikey: String,
    val cid: String,
    val contentType: String,
    val dateUploaded: String,
    val fileName: String,
    val fileSize: Int,
    val id: String,
    val ipfsUri: String,
    val ipfsUrl: String,
    val metadataUrl: String,
    val status: String,
    val user: String
)