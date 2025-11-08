package com.kaillm.webflux_llm.chapter2

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Signal.subscribe
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers
import kotlin.test.Test

class OperatorFlatMapTest {
    /*
    * Mono<Mono<T>> -> Mono<T>
    * Mono<Flux<T>> -> Flux<T>
    * Flux<Mono<T>> -> Flux<T>
    * */
    @Test
    fun monoToFlux() {
        val one: Mono<Int> = Mono.just(1);
        val flatMapMany: Flux<Int> = one.flatMapMany { data ->
            Flux.just(data, data + 1, data + 2)
        }
        flatMapMany.subscribe { data -> println("data = $data") }
    }

    @Test
    fun testWebClientFlatMap() {
        Flux.just(
                callWebClient("1단계 - 문제 이해하기", 1500),
                callWebClient("2단계 - 문제 단계별로 풀어가기", 1000),
                callWebClient("3단계 - 최종 응답", 500),
            )
            .flatMapSequential { monoData -> monoData }
            .subscribe { data -> println("FlatMapped data = $data") }

        // 처음부터 여러 비동기 객체를 합치고 싶다.
        Flux.mergeSequential(
            callWebClient("1단계 - 문제 이해하기", 1500),
            callWebClient("2단계 - 문제 단계별로 풀어가기", 1000),
            callWebClient("3단계 - 최종 응답", 500),
        )
            //.flatMapSequential { monoData -> monoData }
            .subscribe { data -> println("FlatMapped data = $data") }

        // Concat 도 비동기 객체를 합칠 순 있는데, 내부 비동기 publisher가 동기로 수행된다.
        // 따라서 merge operation 보다 비효율적이므로 거의 쓸일 없음
        Flux.concat(
            callWebClient("1단계 - 문제 이해하기", 1500),
            callWebClient("2단계 - 문제 단계별로 풀어가기", 1000),
            callWebClient("3단계 - 최종 응답", 500),
        )
            //.flatMapSequential { monoData -> monoData }
            .subscribe { data -> println("FlatMapped data = $data") }

        Flux.create<Mono<String>> { sink ->
            sink.next(callWebClient("1단계 - 문제 이해하기", 1500))
            sink.next(callWebClient("2단계 - 문제 단계별로 풀어가기", 1000))
            sink.next(callWebClient("3단계 - 최종 응답", 500))
            sink.complete()
        }
            .mergeWith {  }
            .doOnNext { data -> println("doOnNext : $data") }
            .subscribe()

        Thread.sleep(5000)
    }

    private fun callWebClient(request: String, delay: Long): Mono<String> {
        return Mono.defer {
            Thread.sleep(delay)
            Mono.just("$request -> 딜레이: $delay")
        }.subscribeOn(Schedulers.boundedElastic())
    }
}