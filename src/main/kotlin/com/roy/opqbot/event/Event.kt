package com.roy.opqbot.event

import com.roy.opqbot.data.message.currentPacket.CurrentPacket
import com.roy.opqbot.data.message.currentPacket.EventData
import com.roy.opqbot.data.message.currentPacket.EventJoin
import com.roy.opqbot.data.message.eventData.eventBody.MsgBody
import com.roy.opqbot.data.message.eventData.eventHead.GroupInfo
import com.roy.opqbot.data.message.eventData.eventHead.MsgInfo
import com.roy.opqbot.data.message.eventData.eventHead.Sender
import com.roy.opqbot.data.message.eventData.eventHead.UserInfo
import com.roy.opqbot.log.MessageLog
import lombok.Getter
import org.springframework.context.ApplicationEvent

@Getter
class GroupMessageEvent(source: Any?, msgBodyVO: CurrentPacket?) : ApplicationEvent(source!!), EventInterface {

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

    override fun getSender(): Sender? {
        return eventData?.msgHead?.getSenderUser()
    }

    override fun getInfo(): GroupInfo? {
        return eventData?.msgHead?.groupInfo
    }

    override fun getUserInfo(): UserInfo? {
        return eventData?.msgHead?.getUserInfo()
    }

    override fun getMsgInfo(): MsgInfo? {
        return eventData?.msgHead?.getMsgInfo()
    }

    override fun getBot(): Long? {
        return message?.currentQQ
    }

    fun getToString(): String {
        return message.toString()
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
class FriendMessageEvent(source: Any?, msgBodyVO: CurrentPacket?) : ApplicationEvent(source!!), EventFriendInterface {
    private val message: CurrentPacket? = msgBodyVO
    private val eventData: EventData? = message?.currentPacket?.eventData

    fun eventName(): String {
        return message?.currentPacket?.eventName.toString()
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

    override fun getMessages(): MsgBody? {
        if (eventData?.msgBody == null) {
            MessageLog.error("非文本消息解析失败")
            return null
        }
        return eventData.msgBody
    }

    override fun getSender(): Sender? {
        return eventData?.msgHead?.getSenderUser()
    }

    override fun getInfo(): GroupInfo? {
        return eventData?.msgHead?.groupInfo
    }

    override fun getUserInfo(): UserInfo? {
        return eventData?.msgHead?.getUserInfo()
    }

    override fun getMsgInfo(): MsgInfo? {
        return eventData?.msgHead?.getMsgInfo()
    }

    override fun getBot(): Long? {
        return message?.currentQQ
    }

}