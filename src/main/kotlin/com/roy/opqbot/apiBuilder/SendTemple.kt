package com.roy.opqbot.apiBuilder


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SendTemple(
    @SerializedName("CgiCmd")
    @Expose
    val cgiCmd: String?, // QueryUinByUid
    @SerializedName("CgiRequest")
    @Expose
    val cgiRequest: Any?
)