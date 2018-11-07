package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.Robot;
import org.usfirst.frc5933.KitBot2018.subsystems.Arm.ArmPosition;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ResetArm extends Command {
	boolean resetToGround;

    public ResetArm(boolean resetToGroundPosition) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    	
    	resetToGround = resetToGroundPosition;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("Current Command", "ResetArm");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(resetToGround) {
    		Robot.arm.armPositionControl(ArmPosition.VBus, 0.2);
    	}else {
    		Robot.arm.armPositionControl(ArmPosition.VBus, -0.4);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.arm.getLowerArmSwitch() == 1;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	Robot.arm.resetEncoder();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
//    	Robot.arm.moveArmVBus(0);
    }
}
