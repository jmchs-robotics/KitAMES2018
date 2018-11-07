package org.usfirst.frc5933.KitBot2018.subsystems;

import org.usfirst.frc5933.KitBot2018.Robot;
import org.usfirst.frc5933.KitBot2018.RobotMap;
import org.usfirst.frc5933.KitBot2018.commands.DefaultArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Arm extends Subsystem {
	//Arm position constants
	private static final int kArmMaximumEncoderPos = -500;
	private static final int kArmMinimumEncoderPos = 470;
	private static final double kP = 6.0;
	private static final double kI = 0;//0.001;
	private static final double kD = 0;
	private static final double kF = 0;

	/**
	 * The ArmPositions correspond to absolute positions on an encoder
	 * feedback loop that the 'bot will score or get a cube from.
	 * The first three are all positions to acquire a cube, the next three
	 * are to score the cube, and Start should be used when in the pit
	 * and during disabled() to reset the arm for pit work and field setup. Continuous
	 * causes the arm to respond to a fluid set of inputs along its range.
	 */
	public enum ArmPosition {
		CubeOnGround(470),
		CubeOneUp(0),
		CubeTwoUp(0),
		Exchange(430),
		Switch(20),
		Scale(-450),
		Start(0),
		Continuous(0),
		VBus(0);

		private final int pos;
		private ArmPosition(int encoderPos) {
			pos = encoderPos;
		}

		public int getPos() {
			return pos;
		}
	}

	WPI_TalonSRX bigUn = RobotMap.armMotorTestBigUn;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	ArmPosition lastPos = ArmPosition.Start;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new DefaultArm());
	}

	/**
	 * There are some magic numbers in here! They are set in the ArmPosition enum.
	 * @param pos
	 * is the {@link ArmPosition} to move the arm to.
	 * @param continuousInput
	 * is only to be used when in continuous mode. Must be constrained to values between -1 and 1, therefore it is recommended
	 * to only use during teleop in conjunction with a joystick.
	 */
	public void armPositionControl(ArmPosition pos, double continuousInput) {
		lastPos = pos;

		SmartDashboard.putString("Actual last position control", lastPos.name());

		switch(pos) {
		case Continuous:
			//get continuous position from joystick controller
			//Range needs to be condensed to values from -1 to 1
			continuousArmPositionControl(continuousInput);
			break;
		case VBus:
			bigUn.set(ControlMode.PercentOutput, continuousInput);
			break;
		default:
			bigUn.set(ControlMode.Position, pos.getPos());
		}

		if(getLowerArmSwitch() > 0 && !getWithinThreshold(kArmMinimumEncoderPos, 2)) {
			bigUn.setSelectedSensorPosition(ArmPosition.CubeOnGround.pos, 0, 10);
		}
	}

	/**
	 * Maintains the arm in its last position, or through joystick input.
	 * @param continuousInput
	 */
	public void maintainLastArmPosition(double continuousInput) {
		armPositionControl(lastPos, continuousInput);
	}

	/**
	 * Maps the joystick input (-1, 1) to the encoder range for the arm
	 * denoted by (kArmMinimumEncoderPos, kArmMaximumEncoderPos) and
	 * moves the arm to that position.
	 * @param joyInput
	 */
	public void continuousArmPositionControl(double joyInput) {
		//this will be an approximately linear correspondence, and
		//can therefore be represented by y = mx + b, where y is the position to set the arm to.
		//In slope-intercept, this is y - y1 = m(x - x1)
		//m = (y2 - y1)/(x2 - x1)
		//Let y1 = kArmMinimumEncoderPos
		//Let x1 = -1 (joystick minimum input)
		//Let y2 = kArmMaximumEncoderPos
		//let x2 = 1 (joystick maximum input)
		//Let y = encoder position to move arm to
		//Let x = joystick input
		// y - kArmMinimumEncoderPos = (kArmMaximumEncoderPos - kArmMinimumEncoderPos) / (1 - -1) (x - -1)
		//Therefore y = ((kArmMaximumEncoderPos - kArmMinimumEncoderPos)/(2) * (x + 1)) + kArmMinimumEncoderPos

		double y = 0.5 * (kArmMaximumEncoderPos - kArmMinimumEncoderPos) * (joyInput + 1) + kArmMinimumEncoderPos;

		SmartDashboard.putNumber("Arm Target Position", y);
		bigUn.set(ControlMode.Position, y);
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Arm Out: ", bigUn.getMotorOutputPercent());

		SmartDashboard.putNumber("Arm Encoder Pos", bigUn.getSelectedSensorPosition(0));

		SmartDashboard.putNumber("Sub Stick Y", Robot.oi.getSubStick().getY());
		SmartDashboard.putNumber("Sub Stick X", Robot.oi.getSubStick().getX());
	}

	public void init() {
		bigUn.setNeutralMode(NeutralMode.Brake);
		setArmFeedback(10);

		bigUn.configForwardSoftLimitEnable(false, 10);
		bigUn.configReverseSoftLimitEnable(false, 10);

		bigUn.configNominalOutputForward(0, 10);
		bigUn.configNominalOutputReverse(0, 10);
		bigUn.configPeakOutputForward(0.2, 10);//bigUn.configPeakOutputForward(1, 10);
		bigUn.configPeakOutputReverse(-1, 10);
		
		bigUn.configClosedloopRamp(0.5, 10); //seconds to full frontal val. Should help stop swinging. Could also use d-coeff.
	}

	/**
	 * Gets the status of the lower Arm switch (DIP 9) as per hardware upgrade between Utah and Idaho regionals.
	 * @return
	 * 0 if open (false, signal NOT connected to ground); 1 if closed (true, signal connected to ground)
	 */
	public int getLowerArmSwitch() {
		return Robot.roborio.readDips(9, 10, true);
	}	
	
	/**
	 * Gets the status of the upper arm switch (DIP 8) as per hardware upgrade between Utah and Idaho regionals.
	 * @return
	 * 0 if open (false, signal NOT connected to ground); 1 if closed (true, signal connected to ground)
	 */
	public int getUpperArmSwitch() {
		return Robot.roborio.readDips(8, 9, true);
	}

	public void setArmFeedback(int timeoutMs) {
		resetEncoder();

		bigUn.config_kP(0, kP, timeoutMs);
		bigUn.config_kI(0, kI, timeoutMs);
		bigUn.config_kD(0, kD, timeoutMs);
		bigUn.config_kF(0, kF, timeoutMs);
	}

	public void resetEncoder() {
		bigUn.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		bigUn.setSelectedSensorPosition(ArmPosition.Start.pos, 0, 10);

		if(getLowerArmSwitch() > 0) {
			bigUn.setSelectedSensorPosition(ArmPosition.CubeOnGround.pos, 0, 10);
		}
		
		if(getUpperArmSwitch() > 0) {
			bigUn.setSelectedSensorPosition(ArmPosition.Scale.pos, 0, 10);
		}
	}

	public boolean getWithinThreshold(int pos, int threshold) {
		// TODO Auto-generated method stub
		return bigUn.getSelectedSensorPosition(0) < pos + threshold && bigUn.getSelectedSensorPosition(0) > pos - threshold;
	}
}

