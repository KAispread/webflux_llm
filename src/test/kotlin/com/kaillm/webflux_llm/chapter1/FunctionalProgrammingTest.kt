package com.kaillm.webflux_llm.chapter1

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux

@SpringBootTest
class FunctionalProgrammingTest {

    @Test
    fun produceOneToNine() {
        val sink = arrayListOf<Int>()
        for (i in 1..9) {
            Thread.sleep(500L)
            sink.add(i)
        }

        val result1 = (1..9)
        val result2 = map(result1) { data -> data * 2 }
        val result3 = filter(result2)
        forEach(result3) { println(it) }

        (1..9)
            .map { s -> s * 2 }
            .filter { s -> s % 4 == 0 }
            .forEach { println(it) }
    }

    @Test
    fun produceOneToNineWithFlux() {
        val create = Flux.create<Int> { sink ->
            for (i in 1..9) {
                Thread.sleep(500L) // blocking code
                sink.next(i)
            }
            sink.complete()
        }

        create.subscribe { data -> println("WebFlux 가 구독중 !! $data") }
        println("Netty 이벤트 루프로 스레드 복귀!!")
    }

    @Test
    fun produceOneToNinFluxOperator() {
        Flux.fromIterable((1..9))
            .map { s -> s * 2 }
            .filter { s -> s % 4 == 0 }
            .subscribe { println(it) }
    }

    fun forEach(sink: List<Int>, consumer: (Int) -> Unit) {
        sink.forEach { println(it) }
    }

    fun map(sink: Iterable<Int>, mapper: (Int) -> Int): List<Int> {
        val newSink = ArrayList<Int>()
        sink.forEach {
            newSink.add(mapper.invoke(it))
        }
        return newSink
    }

    fun filter(sink: List<Int>): List<Int> {
        return sink.filter { s -> s % 4 == 0 }
            .toList()
    }
}