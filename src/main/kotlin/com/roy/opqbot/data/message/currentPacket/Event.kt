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
)
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

class EventInvite(event: Event) {
    private val invitee: String? = event.invitee
    private val invitor: String? = event.invitor
    private val tips: String? = event.tips

    fun getInviteeUin(): String {
        return invitee.toString()
    }

    fun getInvitorUin(): String {
        return invitor.toString()
    }

    fun getTips(): Triple<String, String, Pair<String, String>>? {
        val uinPattern = Regex("uin=\"(.*?)\"")
        val jpPattern = Regex("jp=\"(.*?)\"")

        // 查找邀请者的信息
        val inviteUinMatch = tips?.let { uinPattern.find(it, "<qq uin=".length) }
        val inviteJpMatch = tips?.let { jpPattern.find(it) }

        // 查找被邀请者的信息
        val joinUinMatch = tips?.let { uinPattern.find(it, "nor txt=\"加入了群聊。\"/><qq uin=".length) }
        val joinJpMatch = tips?.let { jpPattern.find(it, "nor txt=\"加入了群聊。\"/>".length) }

        return if (inviteUinMatch != null && joinUinMatch != null && inviteJpMatch != null && joinJpMatch != null) {
            Triple(
                inviteUinMatch.groupValues[1].removePrefix("u_"),
                joinUinMatch.groupValues[1].removePrefix("u_"),
                inviteJpMatch.groupValues[1] to joinJpMatch.groupValues[1]
            )
        } else {
            null
        }
    }
}
