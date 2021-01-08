package ru.vood.kotlin.kotlincoroutines.chain


import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import ru.vood.kotlin.kotlincoroutines.extension.TooMatchDelay

@Service
@Order(10)
class SimpleCoroutine(private val tooMatchDelay: TooMatchDelay) : Chain {

    var logger: Logger = LoggerFactory.getLogger(SimpleCoroutine::class.java)

    override fun run() {
        runBlocking { // this: CoroutineScope
            var someWork = ""
            launch(newSingleThreadContext("MyOwnThread")) { // launch a new coroutine in the scope of runBlocking
                someWork = tooMatchDelay.someWork()
                logger.info("World!")
            }
            logger.info("Hello")
            logger.info(someWork)
        }
    }

}