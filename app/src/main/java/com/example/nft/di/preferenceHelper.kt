package com.example.nft.di

import android.content.Context
import android.content.SharedPreferences

class preferenceHelper(context : Context): AppPreferencesHelper  {

    private val walletPreferences: SharedPreferences =
        context.getSharedPreferences("Wallet_ID", Context.MODE_PRIVATE)

    override var wid: String?
        get() = walletPreferences.getString("Wallet_ID", null)
        set(value) = walletPreferences.edit().putString("Wallet_ID", value).apply()

}