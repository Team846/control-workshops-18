package com.lynbrookrobotics.workshops.exercises

import com.fazecast.jSerialComm.SerialPort
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedback.Encoder1
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedbackType.Period
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedbackType.Ticks
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.Mode.DirectionAndDutyCycle
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.MotorOutput.Motor1
import com.lynbrookrobotics.usbcontrolsystem.PololuMicroGearMotor
import com.lynbrookrobotics.usbcontrolsystem.cap
import com.lynbrookrobotics.usbcontrolsystem.runPeriodic
import com.lynbrookrobotics.workshops.utility.CsvControlSystemGrapher

inline fun standardSetup(crossinline f: (microseconds: Long, position: Int, velocity: Double) -> Double) {
    val mcu = connect()
    val log = log()
    val motorSpec = PololuMicroGearMotor()

    runPeriodic(10 * 1000) {

        val time = mcu.microsTimeStamp // microseconds

        val position = mcu[Encoder1, Ticks] // # of ticks

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

        mcu[Motor1] = cap(output.toInt())
        mcu.flush()
    }
}

fun connect(): Microcontroller {
    val allPorts = SerialPort.getCommPorts()

    allPorts.mapIndexed { index, serialPort ->
        "$index\t${serialPort.systemPortName} - ${serialPort.portDescription}"
    }.forEach(::println)

    println("Enter index of serial port:")
    val device = allPorts[readLine()!!.toInt()]

    val mcu = Microcontroller(
            device,
            DirectionAndDutyCycle
    )

    println("Connecting...")
    mcu.flush()
    println("Connected!")

    return mcu
}

fun log(): CsvControlSystemGrapher {
    println("Enter the name of your log file:")
    val fileName = readLine()!!
    return CsvControlSystemGrapher(fileName)
}