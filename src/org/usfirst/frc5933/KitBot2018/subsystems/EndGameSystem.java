package org.usfirst.frc5933.KitBot2018.subsystems;

import org.usfirst.frc5933.KitBot2018.RobotMap;
import org.usfirst.frc5933.KitBot2018.commands.DefaultEndGame;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class EndGameSystem extends Subsystem {

	DoubleSolenoid trigger = RobotMap.EndGamePiston;
	
	Spark climber = RobotMap.endGameClimber;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DefaultEndGame());
    }
    
    public void init() {
    	
    }
    
    @Override
    public void periodic() {
    
    }
    
    public void setTriggerSolenoid(Value val) {
    	trigger.set(val);
    }
    
    public void setClimber(double vBus) {
    	climber.set(vBus);
    }
}