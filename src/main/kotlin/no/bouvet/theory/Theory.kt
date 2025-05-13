package no.bouvet.theory

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import kotlin.concurrent.thread

private val logger = LoggerFactory.getLogger("Theorykt")

fun blockingThread(num: Int) {
    val threads = (1..num).toList().map {
        thread {
            if (it % 1000 == 0) println("Created $it threads")
            Thread.sleep(10_000)
            print(".")
        }
    }
    threads.forEach { it.join() }
}

fun blockingCoroutine(num: Int) {
    runBlocking {
        (1..num).toList().map {
            launch {
                if(it % 1000 == 0) println("Created $it coroutines")
                delay(10000)
                print(".")
            }
        }
    }
}

fun main() {
    //blockingThread(1_000_000)
    blockingCoroutine(1_000_000)
}