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
 *      Point the arm straight up and stop
 * Results:
 *      The arm should be pointing straight upwards
 */
fun main(args: Array<String>) = standardSetup { microseconds, position, velocity ->
    0.0
}