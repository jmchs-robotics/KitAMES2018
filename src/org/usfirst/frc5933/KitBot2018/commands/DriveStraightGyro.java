package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.Robot;
import org.usfirst.frc5933.KitBot2018.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraightGyro extends Command {

	double endVal;
	double vBus;
	double initialHeading;
	boolean useFeedback;
	
    public DriveStraightGyro(double timeToRun, double percentVBus) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	
    	endVal = timeToRun;
    	vBus = -percentVBus;
    	useFeedback = false;
    }
    
    public DriveStraightGyro(double inches, double percentVBus, boolean useEncoders) {
    	requires(Robot.drivetrain);
    	
    	endVal = inches * Drivetrain.kEncoderTicksPerInch;
    	vBus = percentVBus;
    	useFeedback = useEncoders;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try {
    		Thread.sleep(10);
    	}catch(Exception e) {}
    	SmartDashboard.putString("Current Command: ", "DriveStraightGyro");
    	
    	initialHeading = Robot.drivetrain.getGyroHeading();
    	Robot.drivetrain.resetEncoders();
    	
    	if(!useFeedback)
    		setTimeout(endVal);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double proportion = Drivetrain.kPGyroConstant * (Robot.drivetrain.getGyroHeading() - initialHeading);
    	double coefficient = 1;
    	
    	if(useFeedback) {
    		coefficient = Math.pow((endVal - Robot.drivetrain.getRightEncoderPos(0)) / endVal,2/3);
    		coefficient = Robot.drivetrain.thresholdVBus(coefficient);
    	}
    	
    	Robot.drivetrain.tankDrive(coefficient * (vBus - proportion), -coefficient * (vBus + proportion));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(useFeedback) {
    		return Math.abs(Robot.drivetrain.getRightEncoderPos(0)) >= Math.abs(endVal);
    	}
        return isTimedOut();
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