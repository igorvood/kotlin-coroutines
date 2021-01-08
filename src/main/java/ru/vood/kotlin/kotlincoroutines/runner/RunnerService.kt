package ru.vood.kotlin.kotlincoroutines.runner

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.kotlin.kotlincoroutines.chain.Chain
import ru.vood.kotlin.kotlincoroutines.chain.SimpleCoroutine

@Service
class RunnerService(val chain: List<Chain>): CommandLineRunner {
    var logger: Logger = LoggerFactory.getLogger(SimpleCoroutine::class.java)
    override fun run(vararg args: String?) {
        chain.forEach {
            logger.info("==Пример имплементированный в ${it.javaClass}==")
            it.run()
        }
    }
}