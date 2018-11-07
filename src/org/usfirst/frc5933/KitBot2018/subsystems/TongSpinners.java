package org.usfirst.frc5933.KitBot2018.subsystems;

import org.usfirst.frc5933.KitBot2018.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TongSpinners extends Subsystem {
	//Tested intake/launch speeds
	private static final double kTongsIntakeSpeed = 0.5;
	private static final double kTongsLaunchSpeed = -1.0;

	Spark leftTongFrontMotor = RobotMap.tongsLeftTong;
	Spark rightTongFrontMotor = RobotMap.tongsRightTong;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
	public void init() {
		controlFlyWheels(0);
	}

	public void intakeCube() {
		leftTongFrontMotor.set(kTongsIntakeSpeed);
		rightTongFrontMotor.set(kTongsIntakeSpeed);
	}

	public void ejectCube() {
		leftTongFrontMotor.set(kTongsLaunchSpeed);
		rightTongFrontMotor.set(kTongsLaunchSpeed);
	}

	public void controlFlyWheels(double speed) {
		leftTongFrontMotor.set(speed);
		rightTongFrontMotor.set(speed);
		SmartDashboard.putNumber("Flywheel Speed", speed);
	}

}

