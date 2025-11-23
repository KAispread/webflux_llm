package com.kaillm.webflux_llm.model.user.chat

import com.kaillm.webflux_llm.model.llmclient.LlmModel

data class UserChatRequestDto(
    val request: String,
    val llmModel: LlmModel
)