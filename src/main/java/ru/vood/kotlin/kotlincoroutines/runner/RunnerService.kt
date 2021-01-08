package ru.vood.kotlin.kotlincoroutines.runner


import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import ru.vood.kotlin.kotlincoroutines.extension.TooMatchDelay

@Service
@Order(10)
class RunnerService(private val tooMatchDelay: TooMatchDelay) : Chain {

    var logger: Logger = LoggerFactory.getLogger(RunnerService::class.java)

    override fun run() {
        runBlocking { // this: CoroutineScope
            var someWork = ""
            val launch = launch { // launch a new coroutine in the scope of runBlocking
                someWork = tooMatchDelay.someWork()
                logger.debug("World!")
            }
            logger.debug("Hello")
            logger.debug(someWork)
        }
    }

}