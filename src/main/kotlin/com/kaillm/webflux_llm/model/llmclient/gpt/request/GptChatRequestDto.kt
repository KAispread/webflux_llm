package com.kaillm.webflux_llm.model.llmclient.gpt.request

import com.kaillm.webflux_llm.model.llmclient.LlmChatRequestDto
import com.kaillm.webflux_llm.model.llmclient.LlmModel
import com.kaillm.webflux_llm.model.llmclient.gpt.GptMessageRole

data class GptChatRequestDto(
    val model: LlmModel,
    val stream: Boolean = false,
    val messages: List<GptCompletionRequestDto>, // request, response 의 context 를 넘겨주기 위해 List 로 선언
    val response_format: GptResponseFormat?
) {

    companion object {

        fun from(llmChatRequestDto: LlmChatRequestDto): GptChatRequestDto {
            val responseFormat = if (llmChatRequestDto.useJson) {
                GptResponseFormat("json_object")
            } else {
                null
            }
            val messages = listOf(
                GptCompletionRequestDto(llmChatRequestDto.systemPrompt, GptMessageRole.SYSTEM),
                GptCompletionRequestDto(llmChatRequestDto.userRequest, GptMessageRole.USER)
            );

            return GptChatRequestDto(
                response_format = responseFormat,
                messages = messages,
                model = llmChatRequestDto.llmModel
            )
        }
    }
}