package com.roy.opqbot.apiBuilder

import com.dtflys.forest.annotation.BaseRequest
import com.dtflys.forest.annotation.JSONBody
import com.dtflys.forest.annotation.Post
import com.google.gson.JsonObject
import com.roy.opqbot.apiBuilder.query.QueryJson
import lombok.SneakyThrows
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component

@Component
@ComponentScan
@BaseRequest(
    baseURL = "\${baseApi}/v1"
)
interface IMainFunc {

    /**
     * POST
     * 发送信息相关事件
     * @param data send信息结构
     */
    @Post("/LuaApiCaller?funcname=MagicCgiCmd&timeout=10&qq=\${qq}")
    @SneakyThrows
    fun sendMessage(@JSONBody data: Any?): JsonObject?

    /**
     *
     * @param data 查询信息结构
     * @return 查询信息结果
     */
    @Post("/LuaApiCaller?funcname=MagicCgiCmd&timeout=10&qq=\${qq}")
    @SneakyThrows
    fun queryByUid(@JSONBody data: Any?): QueryJson?

    /**
     * 文件上传
     * @param data 文件上传结构
     * @return 文件上传结果
     * 上传后可发送 sendPicMsg
     */
    @Post("/upload?qq=\${qq}")
    @SneakyThrows
    fun uploadFile(@JSONBody data: Any?): JsonObject?
}