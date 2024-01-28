package com.roy.opqbot.data.message.eventData.eventBody

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AtUinList (
    @SerializedName("Nick")
    @Expose
    val nick: String?, // 昵称
    @SerializedName("Uid")
    @Expose
    val uid: String?, // u_lwAlRGqNzCm89tWkZZCNZQ
    @SerializedName("Uin")
    @Expose
    val uin: Long? // 账号
)
