package ru.vood.kotlin.kotlincoroutines.extension

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Thread.sleep

@Service
class TooMatchDelay {
    var logger: Logger = LoggerFactory.getLogger(TooMatchDelay::class.java)

    fun someWork(): String {
        logger.info("start")
        sleep(1000)
        logger.info("end")
        return "asdasd"
    }
}