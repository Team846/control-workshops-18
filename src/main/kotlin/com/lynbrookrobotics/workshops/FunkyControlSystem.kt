package com.lynbrookrobotics.workshops

import com.fazecast.jSerialComm.SerialPort
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedback.Encoder1
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedbackType.Period
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedbackType.Ticks
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.MotorOutput.Motor1
import com.lynbrookrobotics.usbcontrolsystem.PololuMicroGearMotor
import com.lynbrookrobotics.usbcontrolsystem.cap
import com.lynbrookrobotics.usbcontrolsystem.runPeriodic

// BASE TEMPLATE
fun main(args: Array<String>) {

    val mcu = connect()
    val motorSpec = PololuMicroGearMotor()

    // write initialization code here!

    runPeriodic(10 * 1000) {
        val position = mcu[Encoder1, Ticks] // # of ticks

        val velocity = motorSpec.getSpeed( // % of max speed
                position,
                mcu[Encoder1, Period]
        )

        // write control code here!
        val output = 0.0 // % of max power

        mcu[Motor1] = cap(output.toInt())
        mcu.flush()
    }
}

private fun connect(): Microcontroller {
    val allPorts = SerialPort.getCommPorts()
    allPorts.forEachIndexed { index, serialPort ->
        println(
                "$index\t${serialPort.systemPortName} - ${serialPort.portDescription}"
        )
    }
    println("Enter index of serial port:")
    val device = allPorts[readLine()!!.toInt()]

    return Microcontroller(
            device,
            Microcontroller.Mode.DirectionAndDutyCycle
    ).apply {
        println("Connecting...")
        flush()
        println("Connected!")
    }
}