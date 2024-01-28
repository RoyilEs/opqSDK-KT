package com.roy.opqbot.event
import com.roy.opqbot.data.message.currentPacket.EventData
import com.roy.opqbot.data.message.eventData.eventBody.AtUinList
import com.roy.opqbot.data.message.eventData.eventBody.AtUinLists
import com.roy.opqbot.data.message.eventData.eventHead.GroupInfo

interface EventInterface {
    fun getMessages(): Any?
    fun getSender(): Any?
    fun getInfo(): Any?
    fun getUserInfo(): Any?
    fun getMsgInfo(): Any?
    fun getBot(): Any?
}
// TODO 未处理FriendPic信息
interface EventFriendMsgInterface : EventCommonMsgInterface, ITextMsg {
    fun getFriendUin(): Long?
    fun getFriendUid(): String?
    fun getSenderUin(): Long?
}
// TODO 未处理GroupPic信息
interface EventGroupMsgInterface : EventCommonMsgInterface, ITextMsg {
    fun atBot(): Boolean
    fun getAiInfo(): List<AtUinList>?
    fun getGroupUin(): Long?
    fun getGroupInfo(): GroupInfo?
    fun getSenderNick(): String?
    fun getSenderUid(): String?
    fun getSenderUin(): Long?
    fun containedPic(): Boolean
    fun containedAt(): Boolean
    fun isFromBot(): Boolean

    //TODO 信息暂处理 红包为谢
    fun getMessages(): Any?
}


interface ITextMsg {
    fun getTextContent(): String?
}

interface EventCommonMsgInterface {
    fun getMsgUid(eventData: EventData?): Long? {
        return eventData?.msgHead?.msgUid
    }
    fun getMsgType(eventData: EventData?): Int? {
        return eventData?.msgHead?.msgType
    }
    fun getMsgTime(eventData: EventData?): Long? {
        return eventData?.msgHead?.msgTime
    }
    fun getMsgSeq(eventData: EventData?): Long? {
        return eventData?.msgHead?.msgSeq
    }
    fun getMsgRandom(eventData: EventData?): Long? {
        return eventData?.msgHead?.msgRandom
    }
}