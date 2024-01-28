package com.roy.opqbot.example

import com.roy.opqbot.apiBuilder.IMainFunc
import com.roy.opqbot.apiBuilder.SendBuiler
import com.roy.opqbot.event.FriendMessageEvent
import com.roy.opqbot.event.GroupMessageEvent
import jakarta.annotation.Resource
import lombok.SneakyThrows
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class Friend {

    @Resource
    private lateinit var iMainFunc: IMainFunc

    @Async
    @SneakyThrows
    @EventListener
    fun textSend(event: FriendMessageEvent) {
        val text: String? = event.getMessages()?.content
        if (text == "text") {
            val  sendMsg = SendBuiler.sendFriendMsg(
                event.getFriendUin(),
                "正确的",
            )
            iMainFunc.sendMessage(sendMsg)
        }
    }
}