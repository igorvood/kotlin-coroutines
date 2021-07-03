package ru.vood.kotlin.kotlincoroutines.chain

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import ru.vood.kotlin.kotlincoroutines.extension.TooMatchDelay

@Service
@Order(20)
class CoroutineWithTimeOut(private val tooMatchDelay: TooMatchDelay) : Chain {
    var logger: Logger = LoggerFactory.getLogger(CoroutineWithTimeOut::class.java)
    override fun run() {
        try {
            var someWork = ""
            runBlocking { // this: CoroutineScope
                withTimeout(13L) { // launch a new coroutine in the scope of runBlocking
                    val launch = launch { someWork = tooMatchDelay.someWork() }
                    logger.info("World!")
                }
                logger.info("Hello")
                logger.info(someWork)
            }
        } catch (e: Throwable) {
            logger.info(e.message)
        }
    }

}