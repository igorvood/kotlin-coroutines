package ru.vood.kotlin.kotlincoroutines.chain.batch

import org.slf4j.Logger

data class Trigger(val id: String)


fun runTrg(trg: Trigger, logger: Logger): String   {
    logger.info("begin $trg")
    if (trg.id.toInt()%50==0){
        error("Some err $trg")}
    Thread.sleep(1000)
    logger.info("end $trg")
    return trg.id
}