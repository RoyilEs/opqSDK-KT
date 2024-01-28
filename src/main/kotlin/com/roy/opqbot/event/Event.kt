package com.roy.opqbot.event

import com.roy.opqbot.data.message.currentPacket.CurrentPacket
import com.roy.opqbot.data.message.currentPacket.EventData
import com.roy.opqbot.data.message.currentPacket.EventJoin
import com.roy.opqbot.data.message.eventData.eventBody.AtUinList
import com.roy.opqbot.data.message.eventData.eventBody.AtUinLists
import com.roy.opqbot.data.message.eventData.eventBody.MsgBody
import com.roy.opqbot.data.message.eventData.eventHead.GroupInfo
import com.roy.opqbot.data.message.eventData.eventHead.MsgInfo
import com.roy.opqbot.data.message.eventData.eventHead.Sender
import com.roy.opqbot.data.message.eventData.eventHead.UserInfo
import com.roy.opqbot.log.MessageLog
import jdk.jfr.Event
import lombok.Getter
import org.springframework.context.ApplicationEvent

@Getter
class GroupMessageEvent(source: Any?, msgBodyVO: CurrentPacket?) : ApplicationEvent(source!!), EventGroupMsgInterface {

    private val message: CurrentPacket? = msgBodyVO

    private val eventData: EventData? = message?.currentPacket?.eventData

    fun eventName(): String {
        return message?.currentPacket?.eventName.toString()
    }

    /**
     * 消息链，包含接收到的消息元素
     * @return
     */
    override fun getMessages(): MsgBody? {
        return eventData?.msgBody
    }
//
//    override fun getSender(): Sender? {
//        return eventData?.msgHead?.getSenderUser()
//    }
//
//    override fun getInfo(): GroupInfo? {
//        return eventData?.msgHead?.groupInfo
//    }
//
//    override fun getUserInfo(): UserInfo? {
//        return eventData?.msgHead?.getUserInfo()
//    }
//
//    override fun getMsgInfo(): MsgInfo? {
//        return eventData?.msgHead?.getMsgInfo()
//    }
//
//    override fun getBot(): Long? {
//        return message?.currentQQ
//    }

    fun getToString(): String {
        return message.toString()
    }

    fun getEventData(): EventData? {
        return eventData
    }

    override fun atBot(): Boolean {
        var at: Boolean = false
        for (v in eventData?.msgBody?.atUinLists!!) {
            if (v.uin == message?.currentQQ) {
                at = true
                break
            }
        }
        return at
    }

    override fun getAiInfo(): List<AtUinList>? {
        return eventData?.msgBody?.atUinLists
    }

    override fun getGroupUin(): Long? {
        return eventData?.msgHead?.fromUin
    }

    override fun getGroupInfo(): GroupInfo? {
        return eventData?.msgHead?.groupInfo
    }

    override fun getSenderNick(): String? {
        return eventData?.msgHead?.senderNick
    }

    override fun getSenderUid(): String? {
        return eventData?.msgHead?.senderUid
    }

    override fun getSenderUin(): Long? {
        return eventData?.msgHead?.senderUin
    }

    override fun containedPic(): Boolean {
        return eventData?.msgBody?.images != null
    }

    override fun containedAt(): Boolean {
        return eventData?.msgBody?.atUinLists !=  null
    }

    override fun isFromBot(): Boolean {
        return  eventData?.msgHead?.senderUin == message?.currentQQ
    }

    override fun getTextContent(): String? {
        if (eventData?.msgBody == null) {
            MessageLog.error("非文本消息解析失败")
            return null
        }
        return eventData.msgBody.content
    }
}

class GroupJoinEvent(source: Any?, msgBodyVO: CurrentPacket?) : ApplicationEvent(source!!) {

    private val message: CurrentPacket? = msgBodyVO
    private val eventData: EventData? = message?.currentPacket?.eventData

    fun getGroupCode(): Long? {
        return eventData?.msgHead?.getUserInfo()?.fromUid?.toLong()
    }

    fun getUser(): EventJoin? {
        return eventData?.event
    }
}

@Getter
class FriendMessageEvent(source: Any?, msgBodyVO: CurrentPacket?) : ApplicationEvent(source!!), EventFriendMsgInterface {
    private val message: CurrentPacket? = msgBodyVO
    private val eventData: EventData? = message?.currentPacket?.eventData

    fun eventName(): String {
        return message?.currentPacket?.eventName.toString()
    }

    fun getEventData(): EventData? {
        return eventData
    }

    override fun getFriendUin(): Long? {
        return eventData?.msgHead?.fromUin
    }

    override fun getFriendUid(): String? {
        return eventData?.msgHead?.fromUid
    }

    override fun getSenderUin(): Long? {
        return eventData?.msgHead?.senderUin
    }

    override fun getTextContent(): String? {
        if (eventData?.msgBody == null) {
            MessageLog.error("非文本消息解析失败")
            return null
        }
        return eventData.msgBody.content
    }


}