package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenTongs extends Command {

	boolean useLatch;
	
    public OpenTongs(boolean turnOffSolenoids) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.grabber);
    	useLatch = turnOffSolenoids;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.grabber.openTongs();
    	setTimeout(0.1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(useLatch)
    		return isTimedOut();
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.grabber.turnArmPistonOff();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
