package com.roy.opqbot.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "opqbot")
data class YamlConf(
    /**
     * 服务器映射ip
     */
    val Ip:String,
    /**
     * 挂载QQ号
     */
    val qid:Long
)