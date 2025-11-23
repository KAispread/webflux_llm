package com.kaillm.webflux_llm

import com.kaillm.webflux_llm.config.GptKey
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(GptKey::class)
@SpringBootApplication
class WebfluxLlmApplication

fun main(args: Array<String>) {
    runApplication<WebfluxLlmApplication>(*args)
}
