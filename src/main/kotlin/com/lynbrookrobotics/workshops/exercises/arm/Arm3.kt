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
 *      Turn the arm 90˚ and stop
 *      Wait 1 Second WITHOUT blocking the thread
 *      Turn the arm another 90˚ and stop
 *      Wait 1 Second WITHOUT blocking the thread
 *      Repeat 4 times
 * Results:
 *      Show that your arm was stationary on its target for each 1 second waiting period
 */
fun main(args: Array<String>) = standardSetup { microseconds, position, velocity ->
    0.0
}