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

fun connect(): Microcontroller {
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