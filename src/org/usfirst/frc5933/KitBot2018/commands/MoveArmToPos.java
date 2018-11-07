package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.Robot;
import org.usfirst.frc5933.KitBot2018.subsystems.Arm.ArmPosition;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MoveArmToPos extends Command {

	boolean waitForArm;
	ArmPosition pos;
	
	/**
	 * Allows for convenient position control of the arm
	 * @param position
	 * @param waitForArm
	 */
    public MoveArmToPos(ArmPosition position, boolean waitForArm) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    	
    	pos = position;
    	this.waitForArm = waitForArm;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.armPositionControl(pos, Robot.oi.getSubStick().getY());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.arm.armPositionControl(pos, Robot.oi.getSubStick().getY());
    	SmartDashboard.putString("Arm Control Type:", pos.name());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !waitForArm || Robot.arm.getWithinThreshold(pos.getPos(), 1);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
