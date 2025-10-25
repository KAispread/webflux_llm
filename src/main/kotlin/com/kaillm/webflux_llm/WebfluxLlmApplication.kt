package com.kaillm.webflux_llm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebfluxLlmApplication

fun main(args: Array<String>) {
    runApplication<WebfluxLlmApplication>(*args)
}
