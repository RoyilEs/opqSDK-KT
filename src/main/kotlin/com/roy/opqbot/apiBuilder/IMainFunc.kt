package com.roy.opqbot.apiBuilder

import com.dtflys.forest.annotation.BaseRequest
import com.dtflys.forest.annotation.JSONBody
import com.dtflys.forest.annotation.Post
import com.google.gson.JsonObject
import lombok.SneakyThrows
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component

@Component
@ComponentScan
@BaseRequest(
    baseURL = "\${baseApi}/v1"
)
interface IMainFunc {

    @Post("/LuaApiCaller?funcname=MagicCgiCmd&timeout=10&qq=\${qq}")
    @SneakyThrows
    fun sendMessage(@JSONBody data: Any?): JsonObject?
}