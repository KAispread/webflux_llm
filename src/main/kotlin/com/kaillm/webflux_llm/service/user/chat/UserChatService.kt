package com.kaillm.webflux_llm.service.user.chat

import com.kaillm.webflux_llm.model.user.chat.UserChatRequestDto
import com.kaillm.webflux_llm.model.user.chat.UserChatResponseDto
import reactor.core.publisher.Mono

interface UserChatService {

    fun getOneShotChat(request: UserChatRequestDto): Mono<UserChatResponseDto>
}