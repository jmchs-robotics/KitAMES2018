package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.Robot;
import org.usfirst.frc5933.KitBot2018.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GyroTurn extends Command {
	double targetHeading;
	double vBus;
	double threshold;
	double error;
	
	/**
	 * Instantiate a gyroturn object. Angle is in gryoscope native units.
	 * @param targetAngle
	 * The gyroscope target units (from robot current heading, not from absolute orientation)
	 * @param percentVBus
	 * The maximum turning voltage bus proportion
	 * @param marginOfError
	 * The allowable error to end the command
	 */
    public GyroTurn(double targetAngle, double percentVBus, double marginOfError) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	
    	targetHeading = targetAngle;
    	vBus = percentVBus;
    	threshold = marginOfError;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	targetHeading += Robot.drivetrain.getGyroHeading(); //accomodate for not actually being square on field. Alternative is zeroing the gyro before any of this.
    	SmartDashboard.putNumber("Turn ME: ", threshold);
    	SmartDashboard.putString("Current Command: ", "GyroTurn");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	error = (targetHeading - Robot.drivetrain.getGyroHeading()) * Drivetrain.kPGyroTurnConstant;
    	
    	double leftVal = 1.5 * Robot.drivetrain.thresholdVBus(vBus * error);
    	double rightVal = 1.5 * Robot.drivetrain.thresholdVBus(vBus * error);
    	
    	Robot.drivetrain.tankDrive(leftVal, rightVal);
    	SmartDashboard.putNumber("Left gyro val: ", leftVal);
    	SmartDashboard.putNumber("Right gyro val", rightVal);
    	
    	SmartDashboard.putNumber("GyroTurn error: ", error);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return error < threshold && error > -threshold;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
