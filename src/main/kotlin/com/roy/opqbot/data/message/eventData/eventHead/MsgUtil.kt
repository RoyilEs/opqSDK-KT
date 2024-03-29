package com.roy.opqbot.data.message.eventData.eventHead

import lombok.Data

class MsgUtil

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
data class FromInfo(
    val fromType: Int?, // 1
    val fromUin: Long?,
    val fromUid: String?,
)

@Data
data class ToInfo(
    val toUid: String?,
    val toUin: Long?
)