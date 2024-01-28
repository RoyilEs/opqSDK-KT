package com.roy.opqbot.utils

import com.dtflys.forest.annotation.Body
import com.dtflys.forest.annotation.Headers
import com.dtflys.forest.annotation.Post
import com.mic.opqbot.data.ai.output.OutText

interface Other {
    @Headers(
        "Authorization: Bearer \${apiKey}", "Content-Type: application/json"
    )
    @Post("https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation")
    fun AliBot(@Body text: String?): OutText?
}