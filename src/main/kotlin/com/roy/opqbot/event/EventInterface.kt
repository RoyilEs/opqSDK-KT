package com.roy.opqbot.event

interface EventInterface {
    fun getMessages(): Any?
    fun getSender(): Any?
    fun getInfo(): Any?
    fun getUserInfo(): Any?
    fun getMsgInfo(): Any?
    fun getBot(): Any?
}

interface EventFriendInterface : EventInterface {
    fun getFriendUin(): Long?
    fun getFriendUid(): String?
    fun getSenderUin(): Long?
}