package com.kaillm.webflux_llm.model.llmclient.gpt.response

data class GptChoice(
    val finish_reason: String,
    val message: GptResponseMessageDto
)