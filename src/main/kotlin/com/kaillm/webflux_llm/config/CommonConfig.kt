package com.kaillm.webflux_llm.config

import com.kaillm.webflux_llm.model.llmclient.LlmType
import com.kaillm.webflux_llm.service.llmclient.LlmWebClientService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommonConfig {

    @Bean
    fun getLlmWebClientServiceMap(llmWebClientService: List<LlmWebClientService>): Map<LlmType, LlmWebClientService> {
        return llmWebClientService.associateBy { service -> service.getLlmType() }
    }
}