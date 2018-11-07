package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.Robot;
import org.usfirst.frc5933.KitBot2018.SocketVisionSender;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionTurnToSetTarget extends Command {
	public static final double kPCoefficient = 2;
	
	double error;
	double vBus;
	double target;
	double threshold;
	String visionTarget;

	/**
	 * Instantiate a command to turn the robot to the set vision target
	 * @param vBus
	 * The voltage proportion to turn at. Set negative/positive to change turn direction
	 * @param visionTarget
	 * The {@link SocketVisionSender} constant string
	 * @param threshold
	 * The vision ticks * PCoefficient to end command.
	 */
	public VisionTurnToSetTarget(double vBus, String visionTarget, double threshold) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drivetrain);

		this.vBus = vBus;
		this.visionTarget = visionTarget;
		this.threshold = threshold;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.sender_.setSendData(visionTarget);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		error = 0;
		
		if(visionTarget == SocketVisionSender.StartRFT) error = Robot.rft_.get_degrees_x() * kPCoefficient;
		if(visionTarget == SocketVisionSender.StartDepth) error = Robot.depth_.get_degrees_x() * kPCoefficient;
		if(visionTarget == SocketVisionSender.StartCubeSearch) error = Robot.cube_.get_degrees_x() * kPCoefficient;
		
		double leftVal = Robot.drivetrain.thresholdVBus(vBus * error);
		double rightVal = Robot.drivetrain.thresholdVBus(vBus * error);

		Robot.drivetrain.tankDrive(leftVal, rightVal);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Math.abs(error) <= threshold * kPCoefficient;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
