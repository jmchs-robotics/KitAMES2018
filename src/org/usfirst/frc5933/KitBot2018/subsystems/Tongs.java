package org.usfirst.frc5933.KitBot2018.subsystems;

import org.usfirst.frc5933.KitBot2018.RobotMap;
import org.usfirst.frc5933.KitBot2018.commands.NullCommand;
import org.usfirst.frc5933.KitBot2018.commands.DefaultTongs;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Tongs extends Subsystem {
	DoubleSolenoid tongsPiston = RobotMap.armTongsPiston;
	DoubleSolenoid rampsPiston = RobotMap.EndGamePiston;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new DefaultTongs());
	}
	
	@Override
	public void periodic() {
		SmartDashboard.putString("Solenoid Position: ", tongsPiston.get().toString());
	}	

	public void openTongs() {
		tongsPiston.set(Value.kReverse);
	}

	public void closeTongs() {
		tongsPiston.set(Value.kForward);
	}

	public void turnArmPistonOff() {
		tongsPiston.set(Value.kOff);
	}

	public void init() {
		// TODO Auto-generated method stub
		turnArmPistonOff();
	}
}

