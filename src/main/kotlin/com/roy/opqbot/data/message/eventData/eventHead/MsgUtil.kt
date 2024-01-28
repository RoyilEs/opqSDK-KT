package com.roy.opqbot.data.message.eventData.eventHead

import lombok.Data

class MsgUtil

@Data
data class Sender(
    val nick: String?,
    val uin: Long?,
    val uid: String?
)

@Data
data class MsgInfo(
    val msgRandom: Long?,
    val msgSeq: Long?,
    val msgTime: Long?,
    val msgUid: Long?,
    val msgType: Int?
)

@Data
data class UserInfo(
    val fromType: Int?, // 1
    val fromUin: Long?,
    val fromUid: String?,
    val toUid: String?,
    val toUin: Long?
)