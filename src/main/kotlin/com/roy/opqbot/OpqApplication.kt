package com.roy.opqbot

import com.roy.opqbot.config.YamlConf
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
@EnableConfigurationProperties(YamlConf::class)
class OpqApplication

fun main(args: Array<String>) {
    runApplication<OpqApplication>(*args)
}