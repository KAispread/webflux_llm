package com.kaillm.webflux_llm.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "llm.gpt")
data class GptKey(
    val key: String
)