package com.roy.opqbot.data.message.currentPacket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class EventJoin(
    @SerializedName("AdminUid")
    @Expose
    val adminUid: String?, // u_OWoCswoPQ9myb8wd8DfnMg
    @SerializedName("Uid")
    @Expose
    val uid: String? // u_NzLC85cSbUHlTJ8X82wVRQ
) {
    /**
     * 谁同意
     * @return
     */
    fun getAnotherUid(): String {
        return adminUid.toString()
    }

    /**
     * 谁进来
     * @return
     */
    fun getUids(): String {
        return uid.toString()
    }
}

data class EventExit (
    @SerializedName("Uid")
    @Expose
    val uid: String? // u_NzLC85cSbUHlTJ8X82wVRQ
) {
    /**
     * 谁退出
     */
    fun getUids(): String {
        return uid.toString()
    }

}
