package com.lynbrookrobotics.workshops.exercises.arm

import com.lynbrookrobotics.workshops.exercises.standardSetup

/**
 * During these exercises, make sure to:
 *      NEVER STALL THE MOTOR!!!
 *      NEVER BUMP or TWIST THE ARM!!!
 *
 * Setup:
 *      Before you plug-in your USB cable, set the arm so that it is pointing straight down
 * Code:
 *      Point the arm parallel to the floor and stop
 *      Wait 3 Seconds WITHOUT blocking the thread
 *      Apply 0% power to the motor
 * Results:
 *      The arm should be parallel to the floor
 */
fun main(args: Array<String>) = standardSetup { microseconds, position, velocity ->
    0.0
}