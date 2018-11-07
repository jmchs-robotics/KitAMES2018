package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EndGameClimber extends Command {

	double vbus;
    public EndGameClimber(double vBus) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.ender);
    	vbus = vBus;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ender.setClimber(vbus);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.ender.setClimber(vbus);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
