package com.kaillm.webflux_llm.service.llmclient

import com.kaillm.webflux_llm.model.llmclient.LlmChatRequestDto
import com.kaillm.webflux_llm.model.llmclient.LlmChatResponseDto
import com.kaillm.webflux_llm.model.llmclient.LlmType
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Slf4j
@Service
class GeminiWebClientService(

) : LlmWebClientService {

    override fun getChatCompletion(requestDto: LlmChatRequestDto): Mono<LlmChatResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getLlmType(): LlmType {
        return LlmType.GEMINI
    }
}