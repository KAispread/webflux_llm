package com.kaillm.webflux_llm.model.llmclient

import com.kaillm.webflux_llm.model.user.chat.UserChatRequestDto

data class LlmChatRequestDto(
    val userRequest: String,
    /*
    * systemPrompt 는 높은 강제성을 가짐 -> 응답 포맷을 지정한다거나 등등..
    * */
    val systemPrompt: String,
    val useJson: Boolean = false,
    val llmModel: LlmModel
) {

    companion object {

        fun of(
            userChatRequestDto: UserChatRequestDto,
            systemPrompt: String
        ): LlmChatRequestDto {
            return LlmChatRequestDto(
                llmModel = userChatRequestDto.llmModel,
                userRequest = userChatRequestDto.request,
                systemPrompt = systemPrompt,
            )
        }
    }
}