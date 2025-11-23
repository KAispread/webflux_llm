package com.kaillm.webflux_llm.controller.user

import com.kaillm.webflux_llm.model.user.chat.UserChatRequestDto
import com.kaillm.webflux_llm.model.user.chat.UserChatResponseDto
import com.kaillm.webflux_llm.service.user.chat.UserChatService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
class UserChatController(
    val userChatService: UserChatService
) {

    @PostMapping("/oneshot")
    fun oneShotChat(@RequestBody userChatRequestDto: UserChatRequestDto): Mono<UserChatResponseDto> {
        // process data from service and response data
        return userChatService.getOneShotChat(userChatRequestDto)
    }
}