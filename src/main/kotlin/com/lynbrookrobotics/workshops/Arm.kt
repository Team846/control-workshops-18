package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedback.Encoder1
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedbackType.Period
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.EncoderFeedbackType.Ticks
import com.lynbrookrobotics.usbcontrolsystem.Microcontroller.MotorOutput.Motor1
import com.lynbrookrobotics.usbcontrolsystem.PololuMicroGearMotor
import com.lynbrookrobotics.usbcontrolsystem.cap
import com.lynbrookrobotics.usbcontrolsystem.runPeriodic

/**
 * During these exercises, make sure to:
 *      NEVER STALL THE MOTOR!!!
 *      NEVER BUMP or TWIST THE ARM!!!
 *
 * Prerequisite:
 *      Complete all 3 Wheel exercises
 *
 * Exercise 1:
 *  Setup:
 *      Before you plug-in your USB cable, set the arm so that it is pointing straight down
 *  Code:
 *      Point the arm straight up and stop
 *  Results:
 *      The arm should be pointing straight upwards
 *
 * Exercise 2:
 *  Setup:
 *      Before you plug-in your USB cable, set the arm so that it is pointing straight down
 *  Code:
 *      Point the arm parallel to the floor and stop
 *      Wait 3 Seconds
 *      Apply 0% power to the motor
 *  Results:
 *      The arm should be parallel to the floor
 *
 * Exercise 3:
 *  Setup:
 *      Before you plug-in your USB cable, set the arm so that it is pointing straight down
 *  Code:
 *      Turn the arm 90˚ and stop
 *      Wait 1 Second
 *      Turn the arm another 90˚ and stop
 *      Wait 1 Second
 *      Repeat until you are pointing down again
 *  Results:
 *      Show that your arm was stationary on its target for each 1 second waiting period
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