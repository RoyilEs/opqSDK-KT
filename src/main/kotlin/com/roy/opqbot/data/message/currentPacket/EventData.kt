package com.roy.opqbot.data.message.currentPacket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.roy.opqbot.data.message.eventData.eventBody.MsgBody
import com.roy.opqbot.data.message.eventData.eventHead.MsgHead


data class EventData(
    /**
     * 进群事件
     */
    @SerializedName("Event")
    @Expose
    val eventJoin: EventJoin?, // null
    /**
     * 退群事件
     */
    @SerializedName("Event")
    @Expose
    val eventExit: EventExit?, // null
    /**
     * 消息体
     */
    @SerializedName("MsgBody")
    @Expose
    val msgBody: MsgBody?, // null
    /**
     * 消息头
     */
    @SerializedName("MsgHead")
    @Expose
    val msgHead: MsgHead? // null
) {

}