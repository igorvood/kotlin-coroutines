package ru.vood.kotlin.kotlincoroutines.chain.batch


import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import ru.vood.kotlin.kotlincoroutines.chain.Chain
import java.lang.Thread.sleep

@Service
@Order(40)
class ParalellBatch_2 : Chain {
    private val logger: Logger = LoggerFactory.getLogger(ParalellBatch_2::class.java)
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
                        runTrg(trg, logger)
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
            runBlocking { launch {  runSet(setTrg)} }
        }
    }

    suspend fun runSet(setTrg: Set<Trigger>) = coroutineScope{
        val toList = setTrg.toList()

/*
        val async = async { runTrg(toList[0]) }
        logger.info("divide")
        val async1 = async { runTrg(toList[1]) }
*/

        logger.info("1")
        val async = launch(Dispatchers.IO) { runTrg(toList[0], logger) }
        logger.info("2")
        val async1 = launch { runTrg(toList[1], logger) }
        logger.info("3")
        async.join()
        logger.info("4")
        async1.join()
        logger.info("5")
//        logger.info(await)
//        logger.info(await1)
//        val toSet = setTrg.map {
//            async { runTrg(it) }
//        }

        logger.info("========================== $setTrg")
    }


}