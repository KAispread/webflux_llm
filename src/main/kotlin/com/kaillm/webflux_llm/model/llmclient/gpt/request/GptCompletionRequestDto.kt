package com.kaillm.webflux_llm.model.llmclient.gpt.request

import com.kaillm.webflux_llm.model.llmclient.gpt.GptMessageRole

data class GptCompletionRequestDto(
    val content: String, // chat texts
    val role: GptMessageRole
)