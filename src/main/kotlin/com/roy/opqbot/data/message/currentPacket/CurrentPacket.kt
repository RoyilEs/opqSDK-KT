package com.roy.opqbot.data.message.currentPacket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentPacket(
    @SerializedName("CurrentPacket")
    @Expose
    val currentPacket: CurrentPacketX?,
    @SerializedName("CurrentQQ")
    @Expose
    val currentQQ: Long? // 257207868
)