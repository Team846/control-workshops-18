package com.lynbrookrobotics.workshops

import java.io.File
import java.nio.charset.StandardCharsets.US_ASCII
import kotlin.concurrent.thread

private val writer = File("/tmp/week_five_log.csv")
        .printWriter(US_ASCII)
        .apply {
            println("Seconds,BatteryVoltage,KiloTicks,PercentSpeed,DutyCycle")
            Runtime.getRuntime().addShutdownHook(
                    thread(start = false, block = this::close)
            )
        }

fun log(
        time: Double,
        batteryVoltage: Double,
        position: Int,
        speed: Int,
        output: Double
) {
    val kiloTicks = position / 1E3
    val percentSpeed = speed / FunkyRobot.topSpeed
    val dutyCycle = output * 100

    writer.println("$time,$batteryVoltage,$kiloTicks,$percentSpeed,$dutyCycle")
}

fun flushLog() = writer.flush()