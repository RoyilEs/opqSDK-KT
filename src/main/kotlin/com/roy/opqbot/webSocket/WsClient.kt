package com.roy.opqbot.webSocket

import com.google.gson.Gson
import com.roy.opqbot.config.YamlConf
import com.roy.opqbot.data.message.currentPacket.CurrentPacket
import com.roy.opqbot.enums.EventNameType
import com.roy.opqbot.event.FriendMessageEvent
import com.roy.opqbot.event.GroupExitEvent
import com.roy.opqbot.event.GroupJoinEvent
import com.roy.opqbot.event.GroupMessageEvent
import com.roy.opqbot.log.MessageLog
import jakarta.annotation.Resource
import okhttp3.*
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


//TODO 暂时启动全都需要优化
@Component
class WsClient {

    @Resource
    lateinit var yamlConf: YamlConf

    @Resource
    lateinit var applicationContext: ApplicationContext

    val client = OkHttpClient()

    @Bean(name = ["wsClientBean"])
    fun getConnection(){
        client()
    }

    fun client(): WebSocket {
        val request = Request.Builder().url("ws://${yamlConf.Ip}/ws").build()

        val listener =
            object : WebSocketListener() {
                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosed(webSocket, code, reason)
                    MessageLog.warn("连接关闭 code: $code, reason: $reason")
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    super.onFailure(webSocket, t, response)
                    MessageLog.error("连接断开  t: ${t.message} ,response: ${response?.message}")
                    Thread.sleep(100000)
                    client()
                }

                override fun onOpen(webSocket: WebSocket, response: Response) {
                    super.onOpen(webSocket, response)
                    MessageLog.info("WebSocket连接已建立")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    super.onMessage(webSocket, text)
                    val fromJson = Gson().fromJson(text, CurrentPacket::class.java)
                    val eventName = fromJson.currentPacket?.eventName
                    when (eventName) {
                        EventNameType.ON_EVENT_GROUP_NEW_MSG -> applicationContext.publishEvent(
                            GroupMessageEvent(
                                this,
                                fromJson
                            )
                        )
                        EventNameType.ON_EVENT_GROUP_JOIN -> applicationContext.publishEvent(
                            GroupJoinEvent(
                                this,
                                fromJson
                            )
                        )
                        EventNameType.ON_EVENT_GROUP_EXIT -> applicationContext.publishEvent(
                            GroupExitEvent(
                                this,
                                fromJson
                            )
                        )
                        EventNameType.ON_EVENT_FRIEND_NEW_MSG -> applicationContext.publishEvent(
                            FriendMessageEvent(
                                this,
                                fromJson
                            )
                        )
                    }
                }
            }
        return client.newWebSocket(request, listener)
    }
}