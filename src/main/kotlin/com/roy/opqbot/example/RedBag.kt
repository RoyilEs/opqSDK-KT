package com.roy.opqbot.example

import com.roy.opqbot.apiBuilder.IMainFunc
import com.roy.opqbot.apiBuilder.SendBuiler
import com.roy.opqbot.event.GroupMessageEvent
import com.roy.opqbot.log.MessageLog
import jakarta.annotation.Resource
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.*

@Component
class RedBag {

    @Resource
    lateinit var iMainFunc: IMainFunc

    fun randRedBagMsg() = when (Random().nextInt(4)) {
        0 -> "谢谢老板谢谢老板谢谢老板，给你磕头"
        1 -> "wcwcwc刚进来就有红包吗"
        2 -> "发红包的最帅，爱你爱你爱你"
        3 -> "抢了这么多吗"
        else -> "终于抢到了一次呜呜呜呜呜，谢谢老板"
    }

    @Async
    @EventListener
    fun quicklyOpenTheRedBag(event: GroupMessageEvent) {
        val redBag = event.getMessages()?.redBag ?: return
        val redType = redBag.redType
        if (redType != 6 && redType != 12) return
        Thread.sleep(1500)
        val sendMessage = iMainFunc.sendMessage(SendBuiler.openRedBag(event))
        val money = sendMessage?.get("ResponseData")?.asJsonObject?.get("GetMoney")?.asDouble?.div(100)
        if (money == 0.0) return
        when (redType) {
            6 -> iMainFunc.sendMessage(
                SendBuiler.sendGroupMsg(
                    event.getGroupCode(),
                    randRedBagMsg(),)
            )

            12 -> iMainFunc.sendMessage(
                redBag.wishing?.let {
                    SendBuiler.sendGroupMsg(
                        event.getGroupCode(),
                        it,)
                })
            else -> return
        }
        MessageLog.info("红包金额： $money")
    }
}