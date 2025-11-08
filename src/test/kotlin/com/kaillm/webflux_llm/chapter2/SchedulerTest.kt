package com.kaillm.webflux_llm.chapter2

import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import kotlin.test.Test

@SpringBootTest
class SchedulerTest {

    @Test
    fun test() {
        Mono.just(2)
            .map {
                println("map Thread Name : ${Thread.currentThread().name}")
                it * 2
            }
            // publishOn 이후 방출되는 데이터의 스레드 지정
            .publishOn(Schedulers.parallel())
            .filter {
                println("filter Thread Name : ${Thread.currentThread().name}")
                it % 4 == 0
            }
            // 구독이 시작될 때 전역적으로 적용되는 스레드
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe { println("Mono 가 구독한 Flux!! : $it") }
    }
}