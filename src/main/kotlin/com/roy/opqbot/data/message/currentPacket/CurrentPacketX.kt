package com.roy.opqbot.data.message.currentPacket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentPacketX(
    @SerializedName("EventData")
    @Expose
    val eventData: EventData?,
    @SerializedName("EventName")
    @Expose
    val eventName: String? // ON_EVENT_GROUP_NEW_MSG
)