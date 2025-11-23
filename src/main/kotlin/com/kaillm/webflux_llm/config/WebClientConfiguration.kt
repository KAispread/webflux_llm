package com.kaillm.webflux_llm.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration {

    @Bean
    fun getWebClient(builder: WebClient.Builder): WebClient {
        return builder.build();
    }
}