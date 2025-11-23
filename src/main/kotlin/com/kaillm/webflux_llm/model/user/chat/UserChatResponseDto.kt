package com.kaillm.webflux_llm.model.user.chat

import com.kaillm.webflux_llm.model.llmclient.LlmChatResponseDto

data class UserChatResponseDto(
    val response: String
) {

    companion object {
        fun from(response: String): UserChatResponseDto {
            return UserChatResponseDto(response)
        }

        fun from(responseDto: LlmChatResponseDto): UserChatResponseDto {
            return UserChatResponseDto(responseDto.llmResponse)
        }
    }
}