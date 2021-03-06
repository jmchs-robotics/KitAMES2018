package org.usfirst.frc5933.KitBot2018.commands;

import org.usfirst.frc5933.KitBot2018.subsystems.Arm.ArmPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 * One of (currently) 2 sets of autonomous commands
 * Other autonomous command set is AutonomousTestGroup.java
 * This gets called by Robot.java in autonomousInit()
 *
 */
public class ArmResetTestGroup extends CommandGroup {

    public ArmResetTestGroup() {
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
    	
    	//testResettingArm();
    	driveAmes2018();
    }
   
    private void driveAmes2018() {
    	// disable thrower, in case it was 'left on' from a previous run
    	//addSequential(new ThrowerSetSpeed(0.0));
    	
    	addParallel(new DefaultArm()); // run in the background to to keep calling execute arm
    	//Drive straight to align with pool, 198"
    	addSequential(new DriveStraightGyro( 198, 0.5, true));
    	
    	//
    	//List of Commands to Get Thrower up to Speed
    	//
    	//add command start the motor so we can throw.
    	addSequential(new ThrowerSetSpeed(0.1));
    	// let thrower come up to speed
    	addSequential( new Wait( 1)); 
    	//add command start the motor so we can throw.
    	addSequential(new ThrowerSetSpeed(0.2));
    	// let thrower come up to speed
    	addSequential( new Wait( 1)); 
    	//add command start the motor so we can throw.
    	addSequential(new ThrowerSetSpeed(0.3));
    	// let thrower come up to speed
    	addSequential( new Wait( 1));
    	
    	
    	double w1 = 1.0;
    	double w2 = 1.0;
    	//add command to drop balls in.(5 times)
    	/**addSequential(new OpenTongs(true));
    	addSequential(new Wait(w1));
    	addSequential(new CloseTongs(true));
    	addSequential(new Wait(w2));
    	
    	addSequential(new OpenTongs(true));
    	addSequential(new Wait(w1));
    	addSequential(new CloseTongs(true));
    	addSequential(new Wait(w2));
    	
    	addSequential(new OpenTongs(true));
    	addSequential(new Wait(w1));
    	addSequential(new CloseTongs(true));
    	addSequential(new Wait(w2));
    	
    	addSequential(new OpenTongs(true));
    	addSequential(new Wait(w1));
    	addSequential(new CloseTongs(true));
    	addSequential(new Wait(w2));
    	
    	addSequential(new OpenTongs(true));
    	addSequential(new Wait(w1));
    	addSequential(new CloseTongs(true));
    	addSequential(new Wait(w2));
    	*/
    	
    	//Drive straight to score line.
    	addSequential(new DriveStraightGyro( 152, 0.7, true)); // 'dummy' call because every other call fails
    	addSequential(new DriveStraightGyro( 152, 0.7, true)); //added another 2 feet to reach alcove
    	//Testing for backing up: should be 24" trying 50"
    	addSequential( new DriveStraightGyro(-50,0.7,true)); // another 'Dummy' call
    	addSequential( new DriveStraightGyro(-50,0.7,true)); //Back out of the alcove
    	//Turn left ... 90 degrees
    	addSequential(new GyroTurn(-90, 0.7, 0.05));
    	/*
    	//Drive Straight for 960'' + the length of Kit
    	addSequential(new DriveStraightGyro(960,.7,true));
    	//Turn 90 degrees to the left
    	//Drive Straight for 336'' + the length of kit.
    	addSequential(new DriveStraightGyro(336,.7,true));
    	//Turn 90 degrees to the left
    	// Drive another 960'' + length of kit'
    	addSequential(new DriveStraightGyro(960,.7,true));
    	//turn 90* L
    	//Drive straight another 336'' to complete a full lap.
    	addSequential(new DriveStraightGyro(336,.7,true));
    	*/
    }
    
    private void testResettingArm() {
    	addSequential(new ResetArm(true));
    	
    	addSequential(new MoveArmToPos(ArmPosition.Scale, false));
    }
    
} 
