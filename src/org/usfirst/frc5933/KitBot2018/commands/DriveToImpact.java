package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.Robot;
import org.usfirst.frc5933.KitBot2018.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToImpact extends Command {

	double initHeading;
	double vBus;
	double threshold;
	double lastAccel = 0;
	
    public DriveToImpact(double voltageBus, double accelerationToStop) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	
    	vBus = voltageBus;
    	threshold = accelerationToStop;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initHeading = Robot.drivetrain.getGyroHeading();
    	SmartDashboard.putString("Current Command: ", "DriveToImpact");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double proportion = Drivetrain.kPGyroConstant * (Robot.drivetrain.getGyroHeading() - initHeading);
    	Robot.drivetrain.tankDrive((vBus - proportion), -(vBus + proportion));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() { 
    	return Robot.roborio.getYAccel() < (threshold) * -Math.signum(vBus);
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
