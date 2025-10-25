package com.kaillm.webflux_llm

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@SpringBootTest
class SubscriberPublisherAsyncText {

    @Test
    fun produceOneToNinFluxOperator() {
        val create: Flux<Int> = Flux.create { sink ->
            for (i in 1..9) {
                Thread.sleep(500L) // blocking code
                sink.next(i)
            }
            sink.complete()
        }.subscribeOn(Schedulers.boundedElastic())

        create.subscribe { data ->
            println("처리 되고 있는 스레드 이름 : ${Thread.currentThread().name}")
            println("WebFlux 가 구독중 !! $data")
        }
        println("Event Loop 로 복귀!!")
        Thread.sleep(5000L)
    }
}