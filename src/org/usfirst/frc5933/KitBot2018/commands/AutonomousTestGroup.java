package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.Robot;
import org.usfirst.frc5933.KitBot2018.SocketVisionSender;
import org.usfirst.frc5933.KitBot2018.subsystems.Arm.ArmPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutonomousTestGroup extends CommandGroup {

	/**in MIDDLE, Exchange Only, put jumper on slot 0 (GND to S)
	 * on LEFT side of scale, put jumper on slot 1 (GND to S)
	 * on RIGHT side of scale, put jumper on slot 2 (GND to S)
	 * in MIDDLE, jumper in slot 5: no robots will block us
	 * in MIDDLE, jumper in slot 3: go to right; other robot will block our left
	 * in MIDDLE, jumper in slot 4: go to left; other robot will block our right
	*/
	public AutonomousTestGroup(String message) {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		//      addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		//      addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.

		//every start needs to have the arm reset
	//	addSequential(new ResetArm());
	//	addSequential(new MoveArmToPos(ArmPosition.CubeOnGround, false));
		
		//every start needs to move forward a foot to not spin into the wall
		addSequential(new DriveStraightGyro(12, 1, true));
		
		//every start should put the arm back down into a neutral position.
		addSequential(new MoveArmToPos(ArmPosition.Start, false));

		if(Robot.roborio.readDips(0, 5, true) == 2) { //on LEFT side of scale, put jumper on slot 1 (GND to S)
			if(message.substring(0 , 1).equalsIgnoreCase("r") && !message.substring(1, 2).equalsIgnoreCase("l")) {// if need to score on right go past switch and barrel across field
				addSequential(new DriveStraightGyro(220, 1, true));
//
//				addSequential(new GyroTurn(60, 1, .025));
//				addSequential(new GyroTurn(20, .3, .025));
//
//				addSequential(new DriveStraightGyro(230, 1, true));
//
//				addSequential(new GyroTurn(60, 1, .025));
//				addSequential(new GyroTurn(20,.3,.025));
//
//				addSequential(new DriveStraightGyro(100, 1, true));
//
//				addSequential(new GyroTurn(60, 1, .025));
//				addSequential(new GyroTurn(20,.3,.025));
				
				addSequential(new DriveStraightGyro(-220, 1, true));
				
			}else {												//if don't need to score on right go ahead and score on left.
				addSequential(new DriveStraightGyro(140, 1, true));

				addSequential(new GyroTurn(60, 1, .025));
				addSequential(new GyroTurn(20, .3, .025));
				
			}
			addParallel(new MoveArmToPos(ArmPosition.Switch, false));
			addSequential(new DriveToImpact(1, .8));

			addSequential(new OpenTongs(true));
			addSequential(new EjectCube(0.2));
			
		} else if (Robot.roborio.readDips(0, 5, true) == 4){ //on RIGHT side of scale, put jumper on slot 2 (GND to S)
			if(message.substring(0 , 1).equalsIgnoreCase("l") && !message.substring(1, 2).equalsIgnoreCase("r")) {// if need to score on left go past switch and barrel across field
				addSequential(new DriveStraightGyro(150, 1, true));
				
				addSequential(new DriveStraightGyro(-150, 1, true));
				
				
				//addSequential(new DriveStraightGyro(220, 1, true));
//
//				addSequential(new GyroTurn(-60, 1, .025));
//				addSequential(new GyroTurn(-20, .3, .025));
//
//				addSequential(new DriveStraightGyro(230, 1, true));
//
//				addSequential(new GyroTurn(-60, 1, .025));
//				addSequential(new GyroTurn(-20,.3,.025));
//
//				addSequential(new DriveStraightGyro(100, 1, true));
//
//				addSequential(new GyroTurn(-60, 1, .025));
//				addSequential(new GyroTurn(-20,.3,.025));
				
			}else {												//if don't need to score on left go ahead and score on right.
				addSequential(new DriveStraightGyro(140, 1, true));

				addSequential(new GyroTurn(-60, .7, .025));
				addSequential(new GyroTurn(-20, .3, .025));

				addSequential(new MoveArmToPos(ArmPosition.Switch, false));
				addSequential(new DriveToImpact(.8, .8));

				addSequential(new OpenTongs(true));
				//addSequential(new EjectCube(0.1));
			}

		} else if(Robot.roborio.readDips(0, 5, true) == 1) { //in MIDDLE, Exchange Only, put jumper on slot 0 (GND to S)
			//exchange (if other robots in our way)
			addSequential(new DriveStraightGyro(20,1,true));

			addSequential(new GyroTurn(-90, 0.4, 0.1));
			
			addSequential(new DriveStraightGyro(41,1,true));

			addSequential(new GyroTurn(-90, 0.4, 0.1));
			
			addSequential(new MoveArmToPos(ArmPosition.Exchange, false));
			addSequential(new DriveToImpact(0.5, 0.5));
			//spit out cube
			addSequential(new EjectCube(1));

			//back up over line
			addSequential(new DriveStraightGyro(-12,1,true));
			addSequential(new GyroTurn(-25, .5, .025));
			addSequential(new DriveStraightGyro(-100, 1, true));

			//don't open tongs in this case until after the cube has been ejected
			addSequential(new MoveArmToPos(ArmPosition.Start, false));
		
		} else if(Robot.roborio.readDips(0, 6, true) == 32){ //in MIDDLE, jumper in slot 5: no robots will block us
			if(message.substring(0, 1).equalsIgnoreCase("l")) {
				SmartDashboard.putString("The MESSAGE contains", "\"L\"");		 //normally, code that should "actually be left" goes here.		
			}else if(message.substring(0, 1).equalsIgnoreCase("r")) {
				SmartDashboard.putString("The MESSAGE contains", "\"R\"");		 //normally, code that should "actually be right" goes here.
			}else {
				SmartDashboard.putString("The MESSAGE contains", "Nothing. Zero. Nada. Zilch. Goose egg. Big ol' empty container. *&^%$^&*^&*^%!!!!!1!1!!");
			}
			
		} else if(message.substring(0, 1).equalsIgnoreCase("l") && Robot.roborio.readDips(0, 4, true) != 8) { //in MIDDLE, NO jumper slots 0-3... if jumper is in 3 other robot will block our left
			//move arm to score in switch
			addParallel(new MoveArmToPos(ArmPosition.Switch, false));

			//left (if message says "l" and no other robot in our way)
			addSequential(new GyroTurn(-40,1,.05));
			addSequential(new DriveStraightGyro(95, 1,true));
			addSequential(new GyroTurn(20,1,.05));
			addSequential(new DriveToImpact(1,.8));

			//spit out cube
			addSequential(new OpenTongs(true));
			addSequential(new EjectCube(0.2));

		} else if(message.substring(0, 1).equalsIgnoreCase("r") && Robot.roborio.readDips(0, 5, true) != 16){ //in MIDDLE, NO jumper slots 0-4... if jumper is in 4 other robot will block our right
			//move arm to score in switch
			addParallel(new MoveArmToPos(ArmPosition.Switch, false));

			//right (if message says "r" and no other robot in our way
			addSequential(new GyroTurn(25,1,.05));
			addSequential(new DriveStraightGyro(70, 1,true));
			addSequential(new GyroTurn(-20,1,.05));
			addSequential(new DriveToImpact(1,.8));

			//spit out cube

			addSequential(new OpenTongs(true));
			addSequential(new EjectCube(0.2));
			
		}else if(Robot.roborio.readDips() == 4 && message.substring(1,2).equalsIgnoreCase("r")){ //on RIGHT side, Scale shot, DIPS in 2 
			addParallel(new MoveArmToPos(ArmPosition.Scale, false));
			addSequential(new DriveStraightGyro(225, 1, true));

			addSequential(new GyroTurn(-20, 0.7, .5));
			
			addSequential(new EjectCube(1));
			
			addSequential(new GyroTurn(20, 0.7, .5));
			addParallel(new MoveArmToPos(ArmPosition.CubeOnGround, false));
			addSequential(new DriveStraightGyro(-220, 1, true));

		}else if(Robot.roborio.readDips() == 2 && message.substring(1,2).equalsIgnoreCase("l")){ //on left side, Scale shot, DIPS in 1 
			addParallel(new MoveArmToPos(ArmPosition.Scale, false));
			addSequential(new DriveStraightGyro(225, 1, true));

			addSequential(new GyroTurn(20, 0.7, .5));
			
			addSequential(new EjectCube(1));
			
			addSequential(new GyroTurn(-20, 0.7, .5));
			addParallel(new MoveArmToPos(ArmPosition.CubeOnGround, false));
			addSequential(new DriveStraightGyro(-220, 1, true));
			
		}else {
			//in MIDDLE, put jumper on slot 0 (GND to S) or if skipped past left or right switch
		
			//exchange (if other robot in our way)
			addSequential(new DriveStraightGyro(20,1,true));

			addSequential(new GyroTurn(-60,1,.025));
			addSequential(new GyroTurn(-25, 0.3, 0.025));

			addSequential(new DriveStraightGyro(40,1,true));

			addSequential(new GyroTurn(-60,1,.025));
			//addSequential(new VisionTurnToSetTarget(-0.3, SocketVisionSender.StartDepth, 1));
			addSequential(new GyroTurn(-25, 0.3, 0.025));

//			addParallel(new MoveArmToPos(ArmPosition.Exchange, false));
			addSequential(new DriveToImpact(0.3,.2));
			//addSequential(new DriveStraightVision(0.3, SocketVisionSender.StartDepth, 0.2));
			
			//spit out cube
			addSequential(new EjectCube(1));

			//back up over line
			addSequential(new DriveStraightGyro(-12,1,true));
			addSequential(new GyroTurn(-25, .3, .025));
			addSequential(new DriveToImpact(-1,-.8));

			//don't move arm in this case until after the cube has been ejected
			addSequential(new MoveArmToPos(ArmPosition.Start, false));

		}
		
		addSequential(new CloseTongs(true)); //reset for driver
	}
}
