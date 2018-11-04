package com.lynbrookrobotics.workshops.utility

import java.io.Closeable
import java.io.File
import java.io.Flushable
import java.nio.charset.StandardCharsets.US_ASCII
import java.util.*
import kotlin.concurrent.thread

class CsvControlSystemGrapher(
        name: String = Date().toString(),
        timeUnit: String = "seconds",
        positionUnit: String = "ticks",
        velocityUnit: String = "$positionUnit/$timeUnit",
        outputUnit: String = "duty cycle"
) : Flushable, Closeable {

    private val file = File("$name.csv")
    private val writer = file.printWriter(US_ASCII) // kotlin.io.DEFAULT_BUFFER_SIZE

    init {
        writer.println("$timeUnit,$positionUnit,$velocityUnit,$outputUnit")
        Runtime.getRuntime().addShutdownHook(
                thread(start = false, block = this::close)
        )
    }

    operator fun invoke(time: Double, position: Double, velocity: Double, output: Double) {
        writer.println("$time,$position,$velocity,$output")
    }

    override fun flush() = writer.flush()
    override fun close() = writer.close()
}