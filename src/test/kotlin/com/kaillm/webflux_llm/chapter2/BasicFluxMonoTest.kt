package com.kaillm.webflux_llm.chapter2

import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.test.Test

@SpringBootTest
class BasicFluxMonoTest {

    /**
    * Flux, Mono 시작하는 방법
     * 1. 빈 함수로부터 시작
     * 2. 데이터로부터 시작
    * */
    @Test
    fun testBasicFluxMono() {
        Flux.just(1, 2, 3, 4, 5)
            .map { it * 2 }
            .filter {it % 4 == 0}
            .subscribe { println("Flux가 구독한 Data! - $it") }

        // Mono : 0개부터 1개의 데이터만 방출할 수 있는 stream [Optional 정도?]
        // Flux : 0개 이상의 데이터를 방출할 수 있는 stream [List, Stream]

        Mono.just(2)
            .map { it * 2 }
            .filter {it % 4 == 0}
            .subscribe { println("Mono가 구독한 Data! - $it") }
    }

    @Test
    fun testFluxMonoBlock() {
        // Mono 의 뜻은? 언젠가 데이터가 방출될 수 있는 객체
        val justString: Mono<String> = Mono.just("String")

        // block 을 통해 내부 값을 바로 받아볼 수 있다.
        // 하지만 block 은 함수명 그대로 스레드가 blocked 되어버림
        val block: String? = justString.block()
    }
}