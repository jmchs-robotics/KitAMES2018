package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PrepVision extends Command {

	String stringToSend;
	
	/**
	 * This command ONLY sets up the socketvision doodads for auto. Should be set sequentially once at least
	 * two seconds before a command that requires vision is called.
	 */
    public PrepVision(String stringToSend) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.roborio);
    	
    	this.stringToSend = stringToSend;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.sender_.setSendData(stringToSend);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
