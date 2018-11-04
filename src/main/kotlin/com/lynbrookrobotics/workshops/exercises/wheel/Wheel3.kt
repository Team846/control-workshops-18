package com.lynbrookrobotics.workshops.exercises.wheel

import com.lynbrookrobotics.workshops.exercises.standardSetup

/**
 * During these exercises, make sure to:
 *      NEVER STALL THE MOTOR!!!
 *      NEVER BUMP or TWIST THE WHEEL!!!
 *
 * Setup:
 *      Before you plug-in your USB cable, set the wheel so that two spokes are parallel to the floor
 * Code:
 *      Turn the wheel 10 rotations and stop
 *      Wait for 10 seconds WITHOUT blocking the thread
 *      Turn the wheel -10 rotations and stop
 * Results:
 *      Whenever your wheel stops, your two spokes should be parallel to the floor
 */
fun main(args: Array<String>) = standardSetup { microseconds, position, velocity ->
    0.0
}