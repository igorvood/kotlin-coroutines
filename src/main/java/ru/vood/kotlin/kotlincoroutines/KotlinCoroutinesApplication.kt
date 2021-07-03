package ru.vood.kotlin.kotlincoroutines

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KotlinCoroutinesApplication {
}
fun main(args: Array<String>) {
    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");
    SpringApplication.run(KotlinCoroutinesApplication::class.java, *args)
}
