package com.roy.opqbot.example

import com.google.gson.Gson
import com.roy.opqbot.apiBuilder.IMainFunc
import com.roy.opqbot.apiBuilder.SendBuiler
import com.roy.opqbot.data.ai.input.AliyunAiData
import com.roy.opqbot.data.ai.input.Input
import com.roy.opqbot.data.ai.input.Message
import com.roy.opqbot.data.message.eventData.eventBody.AtUinLists
import com.roy.opqbot.event.GroupExitEvent
import com.roy.opqbot.event.GroupJoinEvent
import com.roy.opqbot.event.GroupMessageEvent
import com.roy.opqbot.log.MessageLog
import com.roy.opqbot.utils.Other
import jakarta.annotation.Resource
import lombok.SneakyThrows
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.*

@Component
class Group {
    @Resource
    lateinit var iMainFunc: IMainFunc

    @Resource
    lateinit var other: Other

    /**
     * 群日志监听器
     */
    @Async
    @SneakyThrows
    @EventListener
    fun logOutput(event: GroupMessageEvent) {
        if (event.isFromBot()) return
        val sender = event.getSenderInfo()
        val info = event.getGroupInfo()
        val messages = event.getMessages()
        println()
        MessageLog.info("code: ${event.getMsgTime()?.msgType.toString()}")
        MessageLog.info("eventName: ${event.getEventName()}")
        MessageLog.info("群号：${info?.groupCode} - (${info?.groupName}) 群成员：${sender?.uin} - (${sender?.nick})")

        if (messages != null && messages.content?.isNotBlank() == true) MessageLog.info("消息：${messages.content}")
        if (messages?.images != null) messages.images.stream()
            .map { "图片链接->: ${it.url}" }
            .forEach { MessageLog.info(it) }
        MessageLog.info("消息链: ${event.getMessages()}")
    }

    /**
     * 进群欢迎公告
     */
    @Async
    @SneakyThrows
    @EventListener
    fun addGroupTips(event: GroupJoinEvent) {
        val groupCode = event.getGroupCode()
        val uid = event.getEventJoin()?.getUids()
        val queryByUid = iMainFunc.queryByUid(SendBuiler.queryUinByUid(uid))
        val uidList = SendBuiler.getUidList(queryByUid!!)
        iMainFunc.sendMessage(
            SendBuiler.sendGroupMsg(
                groupCode,
                "欢迎${uidList?.nick}新朋友~\n" +
                        "本群使用AI自动回复，请勿在群内发送敏感信息，违者将被踢出群聊！\n"+
                        "来了就不准跑哦!"
            )
        )
    }

    /**
     * 退群事件
     */
    @Async
    @SneakyThrows
    @EventListener
    fun exitGroupTips(event: GroupExitEvent) {
        val groupCode = event.getGroupCode()
        val uid = event.getEventExit()?.getUids()
        val queryByUid = iMainFunc.queryByUid(SendBuiler.queryUinByUid(uid))
        val uidList = SendBuiler.getUidList(queryByUid!!)
        val exitMsg =
            when (Random().nextInt(4)) {
                0 -> "谢过，后会有期。"
                1 -> "暂别，感谢相伴。"
                2 -> "珍重。"
                3 -> "再见啦！"
                else -> "挥别诸位"
            }
        iMainFunc.sendMessage(
            SendBuiler.sendGroupMsg(
                groupCode,
                "${uidList?.nick}(${uidList?.uin})退群了，${exitMsg}"
            )
        )
    }



    @Async
    @SneakyThrows
    @EventListener
    fun aiQA(event: GroupMessageEvent) {
        if (!event.atBot()) return
        val aliyunAiData = AliyunAiData(
            model = "qwen-turbo",
            input = Input(listOf(Message(role = "user", content = event.getTextContent()))),
            parameters = null
        )
        val aliBot = other.aliBot(Gson().toJson(aliyunAiData)) ?: return
        val nick = event.getSenderInfo()?.nick
        val uin = event.getSenderInfo()?.uin
        val sendMsg = SendBuiler.sendGroupMsg(
            event.getGroupCode(),
            aliBot.output?.text,
            AtUinLists(nick, uin)
        )
        iMainFunc.sendMessage(sendMsg)
    }

    @Async
    @SneakyThrows
    @EventListener
    fun textSend(event: GroupMessageEvent) {
        val text: String? = event.getTextContent()
        if (text == "text") {
            val  sendMsg = SendBuiler.sendGroupMsg(
                event.getGroupCode(),
                "正确的",
            )
            iMainFunc.sendMessage(sendMsg)
        }
    }

}

