package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedback.Encoder1
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedbackType.Period
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedbackType.Ticks
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.MotorOutput.Motor1
import com.lynbrookrobotics.usbcontrolsystem.PololuMicroGearMotor
import com.lynbrookrobotics.usbcontrolsystem.cap
import com.lynbrookrobotics.usbcontrolsystem.runPeriodic

/**
 * During these exercises, make sure to NEVER STALL THE MOTOR!!!
 *
 * Prerequisite:
 *      Implement a logging mechanism
 *      Graph position, velocity, and output over time
 *
 * Exercise 1:
 *  Code:
 *      Keep the wheel turning at exactly 50% speed
 *  Results:
 *      LIGHTLY stroke the tire with your finger while it is spinning
 *      DO NOT STALL THE MOTOR!!!
 *      Show that your wheel will remain at 50% speed
 *
 * Exercise 2:
 *  Setup:
 *      Before you plug-in your USB cable, set the wheel so that two spokes are parallel to the floor
 *  Code:
 *      Turn the wheel 1 rotation and stop
 *  Results:
 *      The two spokes should be parallel to the floor
 *
 * Exercise 3:
 *  Setup:
 *      Before you plug-in your USB cable, set the wheel so that two spokes are parallel to the floor
 *  Code:
 *      Turn the wheel 10 rotations and stop
 *      Wait for 10 seconds WITHOUT blocking the thread
 *      Turn the wheel -10 rotations and stop
 *  Results:
 *      Whenever your wheel stops, your two spokes should be parallel to the floor
 */
fun main(args: Array<String>) {

    val mcu = connect()
    val motorSpec = PololuMicroGearMotor()

    // write initialization code here!

    runPeriodic(10 * 1000) {
        val currentTime = mcu.microsTimeStamp // microseconds
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