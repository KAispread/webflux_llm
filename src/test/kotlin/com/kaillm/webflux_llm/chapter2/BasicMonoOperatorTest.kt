package com.kaillm.webflux_llm.chapter2

import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.core.scheduler.Schedulers.boundedElastic
import kotlin.test.Test

@SpringBootTest
class BasicMonoOperatorTest {

    // just, empty
    @Test
    fun startMonoFromData() {
        // Data 로부터 시작
        /*
        * just : 데이터로부터 시작
        * */
        Mono.just(1).subscribe { println("data is $it!!") }

        /*
        * empty : 아무런 데이터 없이 시작
        * use_case : 예외가 발생했을 때, 아무런 데이터 없이 Mono empty 전파
        * */
        Mono.empty<Int>().subscribe { println("data is $it!!") }
    }

    // fromCallable -> 동기적인 객체를 반환할 때 많이 사용
    // defer -> Mono 를 반환하고 싶을때 사용
    @Test
    fun startMonoFromFunction() {
        /*
        * fromCallable
        * use_case
        * 1. 동기 코드 임시 마이그레이션
        * 2. RestTemplate 또는 JPA 등의 동기 라이브러리 사용 시
        * */
        Mono.fromCallable {
            // 우리 로직을 실행
            "Hello!"
        }.subscribeOn(boundedElastic())

        /*
        * defer
        * Mono 를 Mono 로 반환
        * use_case
        * 로직을 Mono 로 감싸 별도의 실행 흐름에서 처리하고 싶을 때
        * */
        Mono.defer {
            callWebClient("request!!")
        }.subscribe()

        // 완성된 객체를 만들어서 Mono 로 만들기 때문에 완성된 객체를 만들기 위해 메인 스레드가 block
        Mono.just("Hello!")
    }

    @Test
    fun testDataNecessity() {
        // A
        val a = "안녕"
        val b = "하세"
        val c = "요"
        val callWebClient: Mono<String> = callWebClient(a + b + c)

        // B
        val defer: Mono<String> = Mono.defer {
            val innerA = "안녕"
            val innerB = "하세"
            val innerC = "요"
            callWebClient(innerA + innerB + innerC)
        }.subscribeOn(boundedElastic())

        // A 는 문자열을 가공하는 작업이 메인스레드에서 즉시 이루어진다.
        // B 는 문자열을 가공하는 작업이 boundedElastic 스레드에서 이루어진다. 메인 스레드가 blocked 되지 않음
    }

    fun callWebClient(request: String): Mono<String> {
        return Mono.just("$request / callWebClient")
    }

    @Test
    fun monoToFlux() {
        Mono.just(1)
            .flatMapMany {
                Flux.just(it, it + 1, it + 2)
            }
            .subscribe { data -> println("data is $data") }
    }
}