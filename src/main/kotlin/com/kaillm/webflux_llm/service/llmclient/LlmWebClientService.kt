package com.kaillm.webflux_llm.service.llmclient

import com.kaillm.webflux_llm.model.llmclient.LlmChatRequestDto
import com.kaillm.webflux_llm.model.llmclient.LlmChatResponseDto
import com.kaillm.webflux_llm.model.llmclient.LlmType
import reactor.core.publisher.Mono

interface LlmWebClientService {
    fun getChatCompletion(requestDto: LlmChatRequestDto): Mono<LlmChatResponseDto>
    fun getLlmType(): LlmType;
}