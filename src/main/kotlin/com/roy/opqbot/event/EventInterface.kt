package com.roy.opqbot.event
import com.roy.opqbot.data.message.currentPacket.EventData

interface EventInterface {
    fun getMessages(): Any?
    fun getSender(): Any?
    fun getInfo(): Any?
    fun getUserInfo(): Any?
    fun getMsgInfo(): Any?
    fun getBot(): Any?
}
// TODO 未处理Pic信息
interface EventFriendMsgInterface : EventCommonMsgInterface, ITextMsg {
    fun getFriendUin(): Long?
    fun getFriendUid(): String?
    fun getSenderUin(): Long?
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