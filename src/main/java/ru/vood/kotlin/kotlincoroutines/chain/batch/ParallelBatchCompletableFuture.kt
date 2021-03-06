package ru.vood.kotlin.kotlincoroutines.chain.batch


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import ru.vood.kotlin.kotlincoroutines.chain.Chain
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ForkJoinPool




@Service
@Order(40)
class ParallelBatchCompletableFuture : Chain {
    private val logger: Logger = LoggerFactory.getLogger(ParallelBatchCompletableFuture::class.java)
    val batchTrgs = listOf(
        (1..10000).map { q -> Trigger(q.toString()) }.toSet(),
        (11000..20000).map { q -> Trigger(q.toString()) }.toSet(),
        (21000..30000).map { q -> Trigger(q.toString()) }.toSet()
    )


    override fun run() {
        val now1: LocalDateTime = LocalDateTime.now()
        r()
        val now2: LocalDateTime = LocalDateTime.now()

        val count = batchTrgs.flatten().count().toDouble()
        val toSeconds = Duration.between(now1, now2).toSeconds().toDouble()
        logger.info("$toSeconds to $count  second per trg ${toSeconds / count}  ")

    }

    fun r() {
        batchTrgs.forEach { setTrg ->
            runSet(setTrg)
        }
    }

    fun runSet(setTrg: Set<Trigger>) {
        val pool = ForkJoinPool(10)
        val toList = setTrg.map {
            val supplyAsync: CompletableFuture<String> = CompletableFuture.supplyAsync { runTrg(it, logger) }
            supplyAsync
        }.toList()

        pool.submit{}

        toList.forEach { q -> q.join() }


        logger.info("========================== $setTrg")
    }


}