package com.kaillm.webflux_llm.controller

import com.kaillm.webflux_llm.log
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Slf4j
@RequestMapping("/reactive")
@RestController
class ReactiveProgrammingExampleController {

    @GetMapping("/onenine/list")
    fun produceOneToNine(): List<Int> {
        val sink = arrayListOf<Int>()
        for (i in 1..9) {
            Thread.sleep(500L)
            sink.add(i)
        } // 4.5 초 소요
        return sink
    }

    @GetMapping("/onenine/flux")
    fun produceOneToNineWithFlux(@RequestHeader("Accept") accept: String): Flux<Int> {
        println("Accept header is : $accept")
        return Flux.create { sink ->
            for (i in 1..9) {
                log.info { "현재 처리하고 있는 스레드명 ${Thread.currentThread().name}" }
                Thread.sleep(500L) // blocking code
                sink.next(i)
            }
            sink.complete()
        }
        // 리액티브 스트리 구현체 Flux, Mono 를 사용하여 발생하는 데이터를 바로바로 리액티브하게 처리
        // 비동기로 동작 - Non-blocking 하게 동작해야한다.

        // 리액티브 프로그래밍 필수 요소
        // 1. 데이터가 준비 될 때마다 바로바로 리액티브하게 처리
        //   > Reactive streams 구현체 Flux, Mono 를 사용
        // 2. 로직을 짤 때는 반드시 Non-blocking 하게 짜야한다.
    }

    @GetMapping("/onenine/flux/v2")
    fun produceOneToNineWithFluxV2(@RequestHeader("Accept") accept: String): Flux<Int> {
        println("Accept header is : $accept")
        return Flux.create<Int> { sink ->
            for (i in 1..9) {
                log.info { "현재 처리하고 있는 스레드명 ${Thread.currentThread().name}" }
                Thread.sleep(500L) // blocking code
                sink.next(i)
            }
            sink.complete()
        }.subscribeOn(Schedulers.boundedElastic())
    }

    @GetMapping("/onenine/flux/v3")
    fun produceOneToNineWithFluxV3(@RequestHeader("Accept") accept: String): Flux<Int> {
        println("Accept header is : $accept")
        return Flux.fromIterable(1..9)
            .delayElements(Duration.ofMillis(500)) // 각 emit 사이에 0.5초 간격
            .doOnNext { log.info { "emit value: $it, thread: ${Thread.currentThread().name}" } }
            .doOnComplete { log.info { "Flux complete!" } }
    }
}