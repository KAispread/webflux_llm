package com.kaillm.webflux_llm.service.user.chat

import com.kaillm.webflux_llm.model.llmclient.LlmChatRequestDto
import com.kaillm.webflux_llm.model.llmclient.LlmType
import com.kaillm.webflux_llm.model.user.chat.UserChatRequestDto
import com.kaillm.webflux_llm.model.user.chat.UserChatResponseDto
import com.kaillm.webflux_llm.service.llmclient.LlmWebClientService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserChatServiceImpl(
    val llmWebClientServiceMap: Map<LlmType, LlmWebClientService>
) : UserChatService {

    override fun getOneShotChat(request: UserChatRequestDto): Mono<UserChatResponseDto> {
        val requestDto = LlmChatRequestDto.of(request, "명령에 적절히 응답해주세요");
        return requireNotNull(llmWebClientServiceMap[request.llmModel.llmType])
            .getChatCompletion(requestDto)
            .map { UserChatResponseDto.from(it) }
    }
}