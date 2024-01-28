package com.roy.opqbot.data.message.eventData.eventBody

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AtUinLists(
    val Nick: String?,
    @SerializedName("Uin")
    @Expose
    val Uin: Long?
)