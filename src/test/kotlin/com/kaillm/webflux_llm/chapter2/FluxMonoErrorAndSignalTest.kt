package com.kaillm.webflux_llm.chapter2

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@SpringBootTest
class FluxMonoErrorAndSignalTest {

    @Test
    fun testBasicSignal() {
        Flux.just(1,2,3,4)
            // java Stream 의 peek() 와 비슷
            .doOnNext { publishData -> println("publishData = $publishData") }
            // complete signal
            .doOnComplete { println("Stream is End") }
            // error signal
            .doOnError { ex -> println("Error occur!! $ex") }
            .subscribe { data -> println("data = $data") }
    }

    @Test
    fun testFluxMonoError() {
        Flux.just(1,2,3,4)
            .map { data ->
                // Reactive Stream 내부에서 발생한 예외는 내부에서 잡는다.
                // main 스레드에 예외가 발생하지 않는다.
                if (data == 3) throw RuntimeException()
                data * 2
            }
            // Exception 을 다른 Exception 으로 던지고 싶을 때
            .onErrorMap { ex -> IllegalArgumentException() }
            // Error 가 발생하면 fallback value 를 내려준다.
            .onErrorReturn(999)
            // Error 가 발생해도 complete signal 을 내려준다.
            .onErrorComplete()
            // Error 가 발생한 예외를 잡아 Consume 하고 다른 데이터는 계속 방출한다.
            //.onErrorContinue()
            // Error 가 발생하면 다른 Inner Publisher 를 수행한다
            //.onErrorResume {  }
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe { data -> println("data = $data") }
    }

    @Test
    fun testFluxMonoDotError() {
        Flux.just(1,2,3,4)
            .flatMap { data ->
                if (data != 3) {
                    Mono.just(data)
                } else {
                    // throw RuntimeException()
                    // 직접 Error signal 을 발생시킨다.
                    Mono.error(RuntimeException())
                }
            }
            .subscribe()
    }
}