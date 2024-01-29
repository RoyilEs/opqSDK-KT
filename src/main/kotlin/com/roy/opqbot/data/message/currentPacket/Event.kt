package com.roy.opqbot.data.message.currentPacket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Event(
    @SerializedName("AdminUid")
    @Expose
    val adminUid: String?, // u_OWoCswoPQ9myb8wd8DfnMg
    @SerializedName("Uid")
    @Expose
    val uid: String?, // u_NzLC85cSbUHlTJ8X82wVRQ
    @SerializedName("Invitee")
    @Expose
    val invitee: String?, //被邀请人Uin
    @SerializedName("Invitor")
    @Expose
    val invitor: String?, //邀请人Uin
    @SerializedName("Tips")
    @Expose
    val tips: String?, //提示信息
) {
    /**
     *  被邀请人Uin 邀请事件
     */
    fun getInviteeUin(): String {
        return  invitee.toString()
    }

    /**
     *  邀请人Uin 邀请事件
     */
    fun getInvitorUin(): String {
        return  invitor.toString()
    }
}

class EventJoin(event: Event) {
    private val adminUid: String ?= event.adminUid
    private val uid: String ?= event.uid

    /**
     * 进群事件处理人
     * @return
     */
    fun getAdminUid(): String {
        return adminUid.toString()
    }

    /**
     * 进群人
     * @return
     */
    fun getUids(): String {
        return uid.toString()
    }
}

class EventExit(event: Event) {
    private val uid: String ?= event.uid

    /**
     * 退群人
     * @return
     */
    fun getUids(): String {
        return uid.toString()
    }
}

