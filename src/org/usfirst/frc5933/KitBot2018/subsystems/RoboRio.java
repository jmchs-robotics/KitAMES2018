package org.usfirst.frc5933.KitBot2018.subsystems;

import org.usfirst.frc5933.KitBot2018.Robot;
import org.usfirst.frc5933.KitBot2018.RobotMap;
import org.usfirst.frc5933.KitBot2018.SocketVisionSender;
import org.usfirst.frc5933.KitBot2018.commands.RoboRioDefault;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RoboRio extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public DigitalInput[] DIPs = RobotMap.DIPs;
	
	public BuiltInAccelerometer accelerometer = RobotMap.roboRioAccelerometer;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new RoboRioDefault());
	}

	/**
	 * Read all digital inputs (0-9) and return an integer representation of their state, summed from their binary inputs.
	 * @return
	 */
	public int readDips() {
		int val = 0;

		for(int i = 0; i < DIPs.length; i++) {
			if(!DIPs[i].get()) { //invert because of pull-up resistors.
				val += (int) Math.pow(2, i);
			}
		}

		return val;
	}

	/**
	 * Read the Digital Inputs between ports beginIndex and endIndex (exclusive). The largest port is 25, so endIndex must be less than 26.
	 * @param beginIndex
	 * The start point to read dips from. Can range from 0 to 24, inclusive. Must always be smaller than endIndex.
	 * @param endIndex
	 * The index to stop reading dips from. Can range from 1 to 26, inclusive. Must always be larger than beginIndex.
	 * @return
	 */
	public int readDips(int beginIndex, int endIndex, boolean startPowersAtZero) {
		int val = 0;
		
		for(int i = beginIndex; i < endIndex; i ++) {
			if(!DIPs[i].get()) { //invert because of pull-up resistors
				if(startPowersAtZero) {
					val += Math.pow(2, i - beginIndex);
				} else {
					val += Math.pow(2, i);
				}
			}
		}
		return val;
	}
	
	public boolean getYAccelerationComparedToThreshold(double threshold, boolean accelerationOver) {
		return (accelerometer.getY() >= threshold) && accelerationOver;
	}
	
	public double getYAccel() {
		return accelerometer.getY();
	}
	
	@Override
	public void periodic() {
		SmartDashboard.putNumber("Y accel: ", getYAccel());
		//SmartDashboard.putString("Sender Output", Robot.sender_.getData());
	}
}