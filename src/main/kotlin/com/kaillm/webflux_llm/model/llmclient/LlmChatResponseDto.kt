package com.kaillm.webflux_llm.model.llmclient

import com.kaillm.webflux_llm.model.llmclient.gpt.response.GptChatResponseDto

data class LlmChatResponseDto(
    val llmResponse: String
) {

    companion object {

        fun from(gptChatResponseDto: GptChatResponseDto): LlmChatResponseDto {
            return LlmChatResponseDto(
                llmResponse = gptChatResponseDto.getSingleChoice().message.content
            )
        }
    }
}
