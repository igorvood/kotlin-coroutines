package ru.vood.kotlin.kotlincoroutines.runner


import kotlinx.coroutines.*
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import ru.vood.kotlin.kotlincoroutines.extension.TooMatchDelay

@Service
@Order(20)
class RunnerServiceWithTimeOut(private val tooMatchDelay: TooMatchDelay): Chain {

    override fun run() {
        var someWork=""
        runBlocking { // this: CoroutineScope
            withTimeout(130L) { // launch a new coroutine in the scope of runBlocking
                someWork = tooMatchDelay.someWork()
                println("World!")
            }
            println("Hello")
            println(someWork)
        }
    }

}