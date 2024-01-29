package com.roy.opqbot.event

import com.roy.opqbot.data.message.currentPacket.*
import com.roy.opqbot.data.message.eventData.eventBody.AtUinList
import com.roy.opqbot.data.message.eventData.eventBody.MsgBody
import com.roy.opqbot.data.message.eventData.eventHead.*

import lombok.Getter
import org.springframework.context.ApplicationEvent

@Getter
class GroupMessageEvent(source: Any?, msgBodyVO: CurrentPacket?) : ApplicationEvent(source!!),
    EventGroupMsgInterface {

    private val message: CurrentPacket? = msgBodyVO

    private val eventData: EventData? = message?.currentPacket?.eventData
    override fun atBot(): Boolean {
        return getAtInfo()?.any { it.uin == message!!.currentQQ } ?: false
    }

    override fun getAtInfo(): List<AtUinList>? {
        return eventData!!.msgBody?.atUinLists
    }

    override fun getGroupCode(): Long? {
        return eventData!!.msgHead?.groupInfo?.groupCode
    }

    override fun getGroupInfo(): GroupInfo? {
        return eventData!!.msgHead?.groupInfo
    }

    override fun getSenderInfo(): Sender? {
        return eventData?.msgHead?.getSenderUser()
    }

    override fun getEventName(): Any? {
        return message!!.currentPacket!!.eventName
    }

    override fun containedPic(): Boolean {
        return getMessages()!!.images!!.isEmpty()
    }

    override fun containedAt(): Boolean {
        return getMessages()!!.atUinLists!!.isEmpty()
    }

    override fun isFromBot(): Boolean {
        return getSenderInfo()!!.uin == message!!.currentQQ
    }

    override fun getMessages(): MsgBody? {
        return eventData!!.msgBody
    }

    override fun getMsgTime(): MsgInfo? {
        return eventData!!.msgHead?.getMsgInfo()
    }


    override fun getTextContent(): String? {
        return getMessages()?.content
    }

    override fun isFromInfo(): FromInfo? {
        return eventData!!.msgHead?.getFromInfo()
    }

    override fun isToInfo(): ToInfo? {
        return eventData!!.msgHead?.getToInfo()
    }

}

class GroupJoinEvent(source: Any?, msgBodyVO: CurrentPacket?) : ApplicationEvent(source!!),
    EventJoinGroupInterface {

    private val message: CurrentPacket? = msgBodyVO
    private val eventData: EventData? = message?.currentPacket?.eventData

    override fun getGroupCode(): Long? {
        return isFromInfo()?.fromUin
    }

    override fun getMsgTime(): MsgInfo? {
        return eventData!!.msgHead?.getMsgInfo()
    }

    override fun getSenderInfo(): Sender? {
        return eventData?.msgHead?.getSenderUser()
    }

    override fun getEventName(): Any? {
        return message!!.currentPacket?.eventName
    }

    fun getEventJoin(): EventJoin? {
        val event = eventData?.event
        return event?.let { EventJoin(it) }
    }

    override fun isFromInfo(): FromInfo? {
        return eventData!!.msgHead?.getFromInfo()
    }

    override fun isToInfo(): ToInfo? {
        return eventData!!.msgHead?.getToInfo()
    }
}

class GroupExitEvent(source: Any?, msgBodyVO: CurrentPacket?) : ApplicationEvent(source!!),
    EventExitGroupInterface {
    private val message: CurrentPacket? = msgBodyVO
    private val eventData: EventData? = message?.currentPacket?.eventData
    override fun getGroupCode(): Long? {
        return isFromInfo()?.fromUin
    }

    override fun getMsgTime(): MsgInfo? {
        return eventData!!.msgHead?.getMsgInfo()
    }

    override fun getSenderInfo(): Sender? {
        return eventData?.msgHead?.getSenderUser()
    }

    override fun getEventName(): Any? {
        return message!!.currentPacket?.eventName
    }

    fun getEventExit(): EventExit? {
        val event = eventData?.event
        return event?.let { EventExit(it) }
    }

    override fun isFromInfo(): FromInfo? {
        return eventData!!.msgHead?.getFromInfo()
    }

    override fun isToInfo(): ToInfo? {
        return eventData!!.msgHead?.getToInfo()
    }
}

class GroupInviteEvent(source: Any?, msgBodyVO: CurrentPacket?) : ApplicationEvent(source!!),
    EventInviteGroupInterface {
    private val message: CurrentPacket? = msgBodyVO
    private val eventData: EventData? = message?.currentPacket?.eventData
    override fun getGroupCode(): Long? {
        return isFromInfo()?.fromUin
    }

    override fun getMsgTime(): MsgInfo? {
        return eventData!!.msgHead?.getMsgInfo()
    }

    override fun getSenderInfo(): Sender? {
        return eventData?.msgHead?.getSenderUser()
    }

    override fun getEventName(): Any? {
        return message!!.currentPacket?.eventName
    }

    fun getEventInvite(): EventInvite? {
        val event = eventData?.event
        return event?.let { EventInvite(it) }
    }

    override fun isFromInfo(): FromInfo? {
        return eventData!!.msgHead?.getFromInfo()
    }

    override fun isToInfo(): ToInfo? {
        return eventData!!.msgHead?.getToInfo()
    }

}
@Getter
class FriendMessageEvent(source: Any?, msgBodyVO: CurrentPacket?): ApplicationEvent(source!!),
    EventFriendMsgInterface {

    private val message: CurrentPacket? = msgBodyVO
    private val eventData: EventData? = message?.currentPacket?.eventData
    override fun getFriendUin(): Long? {
        return eventData!!.msgHead?.fromUin
    }

    override fun getFriendUid(): String? {
        return eventData!!.msgHead?.fromUid
    }

    override fun getSenderUin(): Long? {
        return eventData!!.msgHead?.senderUin
    }

    override fun getMessages(): MsgBody? {
        return eventData!!.msgBody
    }

    override fun getMsgTime(): MsgInfo? {
        return eventData!!.msgHead?.getMsgInfo()
    }

    override fun getSenderInfo(): Sender? {
        return eventData?.msgHead?.getSenderUser()
    }

    override fun getEventName(): Any? {
        return message!!.currentPacket?.eventName
    }

    override fun getTextContent(): String? {
        return getMessages()?.content
    }

    override fun isFromInfo(): FromInfo? {
        return eventData!!.msgHead?.getFromInfo()
    }

    override fun isToInfo(): ToInfo? {
        return eventData!!.msgHead?.getToInfo()
    }
}