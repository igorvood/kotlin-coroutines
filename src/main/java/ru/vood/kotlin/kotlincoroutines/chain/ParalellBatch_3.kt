package ru.vood.kotlin.kotlincoroutines.chain


import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import java.lang.Thread.sleep
import java.time.Duration
import java.time.LocalDateTime

@Service
@Order(40)
class ParalellBatch_3 : Chain {
    private val logger: Logger = LoggerFactory.getLogger(ParalellBatch_3::class.java)
    val batchTrgs = listOf(
        (1..100).map { q -> Trigger(q.toString()) }.toSet(),
        (110..200).map { q -> Trigger(q.toString()) }.toSet(),
        (210..300).map { q -> Trigger(q.toString()) }.toSet()
    )


    override fun run() {
        val now1: LocalDateTime = LocalDateTime.now()
        r()
        val now2: LocalDateTime = LocalDateTime.now()

        val count = batchTrgs.flatten().count().toDouble()
        val toSeconds = Duration.between(now1, now2).toSeconds().toDouble()
        logger.info("$toSeconds to $count  second per trg ${toSeconds/count}  ")

    }
    val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Default + job)

    fun r() {
        batchTrgs.forEach { setTrg ->
            runBlocking { launch {  runSet(setTrg)} }
        }
    }

    suspend fun runSet(setTrg: Set<Trigger>) = coroutineScope{
         setTrg.map {
            launch(Dispatchers.IO) { runTrg(it) }
        }.forEach { it.join() }

        logger.info("========================== $setTrg")
    }

     fun runTrg(trg: Trigger): String   {
        logger.info("begin $trg")
        sleep(1000)
        logger.info("end $trg")
        return trg.id
    }


    data class Trigger(val id: String)

}