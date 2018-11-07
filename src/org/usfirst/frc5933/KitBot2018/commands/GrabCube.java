package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GrabCube extends Command {

    double timeout = -1;

	public GrabCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.spinner);
    }
    
    public GrabCube(double time) {
    	requires(Robot.spinner);
    	
    	timeout = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.spinner.intakeCube();
    
    	if(timeout >= 0) {
    		setTimeout(timeout);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.spinner.intakeCube();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.spinner.controlFlyWheels(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
