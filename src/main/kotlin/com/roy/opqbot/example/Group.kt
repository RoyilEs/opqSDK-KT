package com.roy.opqbot.example

import com.google.gson.Gson
import com.roy.opqbot.apiBuilder.IMainFunc
import com.roy.opqbot.apiBuilder.SendBuiler
import com.roy.opqbot.data.ai.input.AliyunAiData
import com.roy.opqbot.data.ai.input.Input
import com.roy.opqbot.data.ai.input.Message
import com.roy.opqbot.data.message.eventData.eventBody.AtUinLists
import com.roy.opqbot.event.GroupMessageEvent
import com.roy.opqbot.log.MessageLog
import com.roy.opqbot.utils.Other
import jakarta.annotation.Resource
import lombok.SneakyThrows
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class Group {
    @Resource
    lateinit var iMainFunc: IMainFunc

    @Resource
    lateinit var other: Other

//    @Async
//    @SneakyThrows
//    @EventListener
//    fun logOutput(event: GroupMessageEvent) {
//        if (event.getSender()?.uin == event.getBot()) return
//        val sender = event.getSender()
//        val info = event.getInfo()
//        val messages = event.getMessages()
//        println()
//        MessageLog.info("code: ${event.getMsgInfo()?.msgType.toString()}")
//        MessageLog.info("eventName: ${event.eventName()}")
//        MessageLog.info("群号：${info?.groupCode} - (${info?.groupName}) 群成员：${sender?.uin} - (${sender?.nick})")
//
//        if (messages != null && messages.content?.isNotBlank() == true) MessageLog.info("消息：${messages.content}")
//        if (messages?.images != null) messages.images.stream()
//            .map { "图片链接->: ${it.url}" }
//            .forEach { MessageLog.info(it) }
//        MessageLog.info("消息链: ${event.getMessages()}")
//    }

    @Async
    @SneakyThrows
    @EventListener
    fun aiQA(event: GroupMessageEvent) {
        if (event.atBot()) {
            val aliyunAiData = AliyunAiData(
                model = "qwen-turbo",
                input = Input(listOf(Message(role = "user", content = event.getTextContent()))),
                parameters = null
            )
            val aliBot = other.AliBot(Gson().toJson(aliyunAiData)) ?: return
            val nick = event.getSenderNick()
            val uin = event.getSenderUin()
            val sendMsg = SendBuiler.sendGroupMsg(
                event.getGroupUin(),
                aliBot.output?.text,
                AtUinLists(nick, uin)
            )
            iMainFunc.sendMessage(sendMsg)
        }

    }

    @Async
    @SneakyThrows
    @EventListener
    fun textSend(event: GroupMessageEvent) {
        val text: String? = event.getTextContent()
        if (text == "text") {
            val  sendMsg = SendBuiler.sendGroupMsg(
                event.getGroupUin(),
                "正确的",
            )
            iMainFunc.sendMessage(sendMsg)
        }
    }

}

