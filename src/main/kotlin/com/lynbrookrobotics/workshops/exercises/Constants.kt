package com.lynbrookrobotics.workshops.exercises

import com.fazecast.jSerialComm.SerialPort
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedback.Encoder1
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedbackType.Period
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedbackType.Ticks
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.Mode.DirectionAndDutyCycle
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.MotorOutput.Motor1
import com.lynbrookrobotics.usbcontrolsystem.graph.CsvGrapher
import com.lynbrookrobotics.usbcontrolsystem.graph.Grapher
import com.lynbrookrobotics.usbcontrolsystem.graph.LiveGrapher
import com.lynbrookrobotics.usbcontrolsystem.limitVoltage
import com.lynbrookrobotics.usbcontrolsystem.runPeriodic

inline fun standardSetup(mcuPort: Int? = null, logFileName: String? = null, crossinline f: (microseconds: Long, position: Int, velocity: Double) -> Double) {
    val mcu = connect(mcuPort)
    val log = log(logFileName)
    val motorSpec = PololuMicroGearMotor()

    repeat(5) { mcu.flush() }
    val x0 = mcu[Encoder1, Ticks]

    runPeriodic(10 * 1000) {

        val time = mcu.microsTimeStamp // microseconds

        val position = mcu[Encoder1, Ticks] - x0 // # of ticks

        val velocity = motorSpec.getSpeed( // % of max speed
                position,
                mcu[Encoder1, Period]
        )

        val output = f(time, position, velocity)

        log(
                time / 1E6,
                position.toDouble(),
                velocity,
                output
        )

        mcu[Motor1] = limitVoltage(output)
        mcu.flush()
    }
}

fun connect(mcuPort: Int? = null): Microcontroller {
    val allPorts = SerialPort.getCommPorts()

    allPorts.mapIndexed { index, serialPort ->
        "$index\t${serialPort.systemPortName} - ${serialPort.portDescription}"
    }.forEach(::println)

    fun getPort(): Int {
        println("Enter index of serial port:")
        return readLine()!!.toInt()
    }

    val device = allPorts[mcuPort ?: getPort()]

    val mcu = Microcontroller(
            device,
            DirectionAndDutyCycle
    )

    println("Connecting...")
    mcu.flush()
    println("Connected!")

    return mcu
}

fun log(logFileName: String? = null): Grapher {

    fun getFileName(): String {
        println("Enter the name of your log file:")
        return readLine()!!
    }

    val fileName = logFileName ?: getFileName()

    val timeUnit = "seconds"
    val positionUnit = "ticks"
    val velocityUnit = "$positionUnit/$timeUnit"
    val outputUnit = "duty cycle"

    return if (fileName.isBlank())
        LiveGrapher(timeUnit, positionUnit, velocityUnit, outputUnit)
    else
        CsvGrapher(fileName, timeUnit, positionUnit, velocityUnit, outputUnit)
}