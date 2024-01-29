package com.roy.opqbot.event
import com.roy.opqbot.data.message.eventData.eventBody.AtUinList
import com.roy.opqbot.data.message.eventData.eventBody.MsgBody
import com.roy.opqbot.data.message.eventData.eventHead.*

interface EventGroupMsgInterface : EventCommonMsgTimeInterface, ITextMsg, EventUserInterFace, EventCommonGroup {
    fun atBot(): Boolean
    fun getAtInfo(): List<AtUinList>?
    fun getGroupInfo(): GroupInfo?
    fun containedPic(): Boolean
    fun containedAt(): Boolean
    fun isFromBot(): Boolean
    fun getMessages(): MsgBody?
}

interface EventFriendMsgInterface : EventCommonMsgTimeInterface, ITextMsg, EventUserInterFace {
    fun getFriendUin(): Long?
    fun getFriendUid(): String?
    fun getSenderUin(): Long?
    fun getMessages(): MsgBody?
}

interface EventJoinGroupInterface : EventCommonGroup, EventCommonMsgTimeInterface, EventUserInterFace

interface EventExitGroupInterface : EventCommonGroup, EventCommonMsgTimeInterface, EventUserInterFace

interface EventCommonGroup {
    fun getGroupCode(): Long?
}

interface EventCommonMsgTimeInterface {
    fun getMsgTime(): MsgInfo?
    fun getSenderInfo(): Sender?
    fun getEventName(): Any?
}

interface EventUserInterFace {
    fun isFromInfo(): FromInfo?
    fun isToInfo(): ToInfo?
}

interface ITextMsg {
    fun getTextContent(): String?
}