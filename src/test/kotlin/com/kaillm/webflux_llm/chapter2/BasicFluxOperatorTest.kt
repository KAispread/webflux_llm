package com.kaillm.webflux_llm.chapter2

import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.core.publisher.Mono
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.Test

@SpringBootTest
class BasicFluxOperatorTest {

    /**
     * Flux
     * 데이터 : just, empty, from-시리즈
     * 함수 : defer, create
     * */
    @Test
    fun testFluxFromData() {
        Flux.just(1,2,3,4)
            .subscribe { data -> println("Received: $data") }

        Flux.fromIterable(listOf(1,2,3,4))
            .subscribe { data -> println("Received from iterable: $data") }

        Flux.defer { Flux.just("Deferred Data") }
            .subscribe { data -> println("Received from defer: $data") }
    }

    /*
    * Flux defer -> 안에서 Flux 객체를 반환해줘야함
    * Flux create -> 안에서 동기적인 객체를 반환해줘야함
    * */
    @Test
    fun testFluxFromFunction() {
        Flux.defer {
            println("Creating Flux in defer")
            Flux.just("Deferred Data 1", "Deferred Data 2")
        }.subscribe { data -> println("Received from defer: $data") }

        Flux.create<String> { sink ->
            println("Creating Flux in create")
            sink.next("Created Data 1")
            sink.next("Created Data 2")
            // sink 가 끝났음을 알려줘야함
            sink.complete()
        }.subscribe { data -> println("Received from create: $data") }
    }

    @Test
    fun testSinkDetail() {
        Flux.create { sink ->
            val counter = AtomicInteger(0)
            recursiveFunction(sink)
            recursiveFunction(sink)
            recursiveFunction(sink)
        }
            // ThreadLocal -> Context
            .contextWrite { ctx -> ctx.put("counter", AtomicInteger(0))}
            .subscribe { data -> println("Received from recursive sink: $data")}
    }

    fun recursiveFunction(sink: FluxSink<String>) {
        val counter = sink.contextView().get<AtomicInteger>("counter")
        if (counter.incrementAndGet() < 10) {
            sink.next("sink count $counter")
            recursiveFunction(sink)
        } else {
            sink.complete()
        }
    }

    /*
    * Flux 에 담긴 여러 Elements 를 Mono<List<Int>> 로 변환
    * */
    @Test
    fun testFluxCollectList() {
        val collectList: Mono<List<Int>> = Flux.just(1, 2, 3, 4, 5)
            .map { data -> data * 2 }
            .filter { data -> data % 4 == 0 }
            .collectList();

        collectList.subscribe { data -> print("collectList가 반환한 List data! = $data") }
    }
}