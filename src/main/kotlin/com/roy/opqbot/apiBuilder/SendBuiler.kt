@file:Suppress("UNUSED_EXPRESSION")

package com.roy.opqbot.apiBuilder

import com.roy.opqbot.data.message.eventData.eventBody.AtUinLists
import com.roy.opqbot.data.message.eventData.eventBody.RedBag
import com.roy.opqbot.event.GroupMessageEvent
import com.roy.opqbot.apiBuilder.query.QueryJson
import com.roy.opqbot.apiBuilder.query.ResponseData
import com.roy.opqbot.utils.Utils
import lombok.SneakyThrows
import net.coobird.thumbnailator.Thumbnails
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*


object SendBuiler {
    private val pbSend = "MessageSvc.PbSendMsg"
    private val query = "QueryUinByUid"
    private val upload = "PicUp.DataUp"
    private val revoke = "GroupRevokeMsg"
    private val openredbag = "OpenREDBAG"

    /**
     * 发送@At群聊消息结构体
     * @param groupCode 群号
     * @param message 消息内容
     * @param atUinList @see AtUinLists
     * @return 发送消息的模板
     */
    fun sendGroupMsg(groupCode: Long?, message: Any?, atUinList: AtUinLists?): SendTemple {

        val list = if (atUinList != null)
            listOf(atUinList) else null

        val data = mapOf(
            "ToUin" to groupCode,
            "ToType" to 2,
            "Content" to message,
            "AtUinLists" to list
        )
        return SendTemple(cgiCmd = pbSend, cgiRequest = data)
    }

    /**
     * 发送群聊消息结构体
     *  @param groupCode 群号
     *  @param message 消息内容
     *  @return 发送消息的模板
     */
    fun sendGroupMsg(groupCode: Long?, message: Any?): SendTemple {

        val data = mapOf(
            "ToUin" to groupCode,
            "ToType" to 2,
            "Content" to message,
            "AtUinLists" to null
        )
        return SendTemple(cgiCmd = pbSend, cgiRequest = data)
    }

    /**
     * 发送好友消息结构体
     * @param friendUin 好友QQ号
     * @param message 消息内容
     * @return 发送消息的模板
     */
    fun sendFriendMsg(friendUin: Long?, message: Any?): SendTemple {
        val data = mapOf(
            "ToUin" to friendUin,
            "ToType" to 1,
            "Content" to message
        )
        return SendTemple(cgiCmd = pbSend, cgiRequest = data)
    }


    /**
     * 发送文件方式
     * @constructor Create empty Type
     */
    object Type {
        const val FilePath = "FilePath"
        const val Base64Buf = "Base64Buf"
        const val FileUrl = "FileUrl"
    }

    /**
     *  发送文件类型
     *  1好友图片2群组图片26好友语音29群组语音
     * @constructor Create empty Upload type
     */
    object UploadType {
        const val FriendImage = 1
        const val GroupImage = 2
        const val FriendVoice = 26
        const val GroupVoice = 29
    }


    /**
     * 发送图片
     * @param groupCode
     * @param images
     * @see [Image]
     * @return
     */
    fun sendPicMsg(groupCode: Long?, msgType: String, fileData: Any?): SendTemple {
        val data = mapOf(
            "ToUin" to groupCode,
            "ToType" to 2,
            msgType to if (msgType == Utils.MsgType.Voice) fileData
            else listOf(fileData)
        )
        return SendTemple(cgiCmd = pbSend, cgiRequest = data)
    }


    /**
     * @param t  格式 FilePath Base64Buf FileUrl
     * @param s  Type 上传文件的格式
     * @param c  UploadType 1好友图片2群组图片26好友语音29群组语音
     * @see [UploadType]
     * @see [Type]
     * @return   SendTemple json消息模板
     */
    fun upLoadFile(t: Any?, s: String, c: Int): SendTemple {
        val data = mapOf(
            "CommandId" to c,
            s to t
        )
        return SendTemple(cgiCmd = upload, cgiRequest = data)
    }


    /**
     * Query uin by uid
     *
     * @param uid
     * @return
     */
    fun queryUinByUid(uid: String?): SendTemple {
        val cgiRequest =
            mapOf(
                "Uid" to uid
            )
        return SendTemple(cgiCmd = query, cgiRequest = cgiRequest)
    }


    /**
     * Group revoke msg
     *
     * @param groupCode
     * @param msgSeq
     * @param msgRandom
     * @return
     */
    fun groupRevokeMsg(groupCode: Long?, msgSeq: Long?, msgRandom: Long?): SendTemple {
        val cgiRequest = mapOf(
            "Uin" to groupCode,
            "MsgSeq" to msgSeq,
            "MsgRandom" to msgRandom
        )
        return SendTemple(cgiCmd = revoke, cgiRequest = cgiRequest)
    }

    /**
     * Get uid list
     *
     * @param queryJson
     * @return
     */
    fun getUidList(queryJson: QueryJson): ResponseData? {
        return queryJson.responseData?.map {
            ResponseData(
                head = it?.head,
                level = it?.level,
                mark = it?.mark,
                nick = it?.nick,
                sex = it?.sex,
                signature = it?.signature,
                uin = it?.uin,
                uid = it?.uid
            )
        }?.firstOrNull()
    }

    /**
     * Openred bag
     * @param event
     */
    fun openRedBag(event: GroupMessageEvent): SendTemple {
        val redBag = event.getMessages()?.redBag
        val redBagData = RedBag(
            authkey = redBag?.authkey,
            channel = redBag?.channel,
            des = redBag?.des,
            fromType = redBag?.fromType,
            fromUin = redBag?.fromUin,
            listid = redBag?.listid,
            redType = redBag?.redType,
            stingIndex = redBag?.stingIndex,
            token172 = redBag?.token172,
            token173 = redBag?.token173,
            transferMsg = redBag?.transferMsg,
            wishing = redBag?.wishing
        )
        return SendTemple(cgiCmd = openredbag, cgiRequest = redBagData)
    }


    /**
     * 压缩图片并转换为base64<br>
     * <font color="red">注意：压缩后的图片大小会变小，但是长宽不会变小</font><br>
     *
     * <font color="red">❗只是为了解决图片发送失败问题</font>
     *
     * @param imageBytes 图片字节
     * @param quality    压缩质量 0.0最低质量  1.0最高画质
     * @return
     */
    @SneakyThrows
    fun compressAndEncodeToBase64Thumbnails(imageBytes: ByteArray?, quality: Double): String? {
        val os = ByteArrayOutputStream()
        Thumbnails.of(ByteArrayInputStream(imageBytes))
            .scale(1.0)
            .outputQuality(quality)
            .toOutputStream(os)
        return Base64.getEncoder().encodeToString(os.toByteArray())
    }


    /**
     * 判断消息是否相等<br>
     *
     * @param event
     * @param value
     * @return
     */

    fun <T> T.regularProcessing(data: String): String? {
        return Regex(data).find(this.toString())?.groupValues?.getOrNull(1)
    }

    fun <T> T.messageEquals(event: GroupMessageEvent): Boolean {
        if (event.isFromBot()) return false
        return event.getMessages()?.content!! == this.toString()
    }
}
