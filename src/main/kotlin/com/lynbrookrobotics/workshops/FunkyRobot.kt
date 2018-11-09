package com.lynbrookrobotics.workshops

import com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput
import com.ctre.phoenix.motorcontrol.NeutralMode.Coast
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.RobotController
import edu.wpi.first.wpilibj.RobotState
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.hal.HAL

// WEEK FIVE
class FunkyRobot : RobotBase() {

    companion object { // think of this as Java's static space
        const val topSpeed = 14550.0 // # of ticks per 100ms
    }

    override fun startCompetition() { // think of this as your main method

        val esc = TalonSRX(12).also(::configEsc) // init and config ESC

        esc.setNeutralMode(Coast) // set the ESC into Brake or Coast mode

        // write initialization code here!

        HAL.observeUserProgramStarting() // tell the Driver Station that we are done initializing
        while (true) {
            val time = Timer.getFPGATimestamp() // seconds
            val batteryVoltage = RobotController.getBatteryVoltage() // volts
            val position = esc.getSelectedSensorPosition(pidIdx) // # of ticks
            val speed = esc.getSelectedSensorVelocity(pidIdx) // # of ticks per 100ms

            var output = 0.0 // duty cycle between -1.0 and 1.0

            // write control code here!

            esc.set(PercentOutput, output) // set ESC duty cycle
            log(time, batteryVoltage, position, speed, output) // write to log file
            if (RobotState.isDisabled()) flushLog() // flush the log file when disabled
            m_ds.waitForData() // get new data from the Driver Station
        }
    }
}