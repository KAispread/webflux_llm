package com.kaillm.webflux_llm.model.llmclient.gpt.response

data class GptChatResponseDto(
    val choices: List<GptChoice> // 한 번에 여러 응답이 올 수 있음
) {

    fun getSingleChoice(): GptChoice {
        return choices.stream().findFirst().orElseThrow()
    }
}