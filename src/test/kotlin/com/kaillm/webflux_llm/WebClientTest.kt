package com.kaillm.webflux_llm

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import kotlin.test.Test

@SpringBootTest
class WebClientTest {

    val webClient: WebClient = WebClient.builder().build()

    @Test
    fun testWebClient() {
        val create: Flux<Int> = webClient.get()
            .uri("http://localhost:8080/reactive/onenine/flux/v3")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux<Int>()

        create.subscribe { data ->
            println("처리 되고 있는 스레드 이름 : ${Thread.currentThread().name}")
            println("WebFlux 가 구독중 !! $data")
        }
        println("Event Loop 로 복귀!!")

        Thread.sleep(5000L)
    }
}