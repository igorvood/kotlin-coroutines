package ru.vood.kotlin.kotlincoroutines.chain


import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import java.lang.Thread.sleep

@Service
@Order(40)
class ParalellBatch : Chain {
    private val logger: Logger = LoggerFactory.getLogger(ParalellBatch::class.java)
    val batchTrgs = listOf(
        (1..10).map { q -> Trigger(q.toString()) }.toSet(),
        (11..20).map { q -> Trigger(q.toString()) }.toSet(),
        (21..30).map { q -> Trigger(q.toString()) }.toSet()
    )


    override fun run() {

        r()

    }

    suspend fun runAll(): String {
        batchTrgs.forEach { setTrg ->

            val batch: List<Deferred<String>> = runBlocking {
                setTrg.map { trg ->

                    async<String> {
                        runTrg(trg)
                    }
                }
                    .toList()
            }

            batch.forEach { it.await() }
            logger.info("==========================")

        }
        return "1"
    }


    fun r() {
        batchTrgs.forEach { setTrg ->
            runBlocking { runSet(setTrg) }
        }
    }

    suspend fun runSet(setTrg: Set<Trigger>) {
        runBlocking {
            launch {
                setTrg.parallelStream().forEach {
                     runTrg(it)
                }
//                    .forEach { it.await() }
                logger.info("========================== $setTrg")
            }
        }
    }

     fun runTrg(trg: Trigger): String {
        logger.info("begin $trg")
        sleep(1000)
        logger.info("end $trg")
        return trg.id
    }


    data class Trigger(val id: String)

}