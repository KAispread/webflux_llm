package com.kaillm.webflux_llm.service.llmclient

import com.kaillm.webflux_llm.config.GptKey
import com.kaillm.webflux_llm.log
import com.kaillm.webflux_llm.model.llmclient.LlmChatRequestDto
import com.kaillm.webflux_llm.model.llmclient.LlmChatResponseDto
import com.kaillm.webflux_llm.model.llmclient.LlmType
import com.kaillm.webflux_llm.model.llmclient.gpt.request.GptChatRequestDto
import com.kaillm.webflux_llm.model.llmclient.gpt.response.GptChatResponseDto
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Slf4j
@Service
class GptWebClientService(
    val webClient: WebClient,
    val gptKey: GptKey
) : LlmWebClientService {

    // request 는 OpenAI 의 API 스펙에 맞게 설정해줘야함
    override fun getChatCompletion(requestDto: LlmChatRequestDto): Mono<LlmChatResponseDto> {
        val gptChatRequestDto = GptChatRequestDto.from(requestDto)

        return webClient.post()
            .uri("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer ${gptKey.key}")
            .bodyValue(gptChatRequestDto)
            .retrieve()
            .onStatus(
                { status -> status.is4xxClientError } ,
                { clientResponse ->
                    clientResponse.bodyToMono(String::class.java).flatMap { body ->
                        log.error("Error Response: {}", body)
                        Mono.error(RuntimeException("API 요청 실패: $body"))
                    }
                }
            )
            .bodyToMono(GptChatResponseDto::class.java)
            .map { LlmChatResponseDto.from(it) }
    }

    override fun getLlmType(): LlmType {
        return LlmType.GPT
    }
}