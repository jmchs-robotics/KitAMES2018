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
    	//Drive straight to align with pool.//198''//changed to seconds for testing
    	addSequential(new DriveStraightGyro(8,.7));
    	//add command start the motor so we can throw.
    	addSequential(new ThrowerSetSpeed(0.5));
    	
    	//add command to drop balls in.(5 times)
    	addSequential(new OpenTongs(true));
    	addSequential(new Wait(2.0));
    	addSequential(new CloseTongs(true));
    	addSequential(new Wait(2.0));
    	
    	addSequential(new OpenTongs(true));
    	addSequential(new Wait(2.0));
    	addSequential(new CloseTongs(true));
    	addSequential(new Wait(2.0));
    	
    	addSequential(new OpenTongs(true));
    	addSequential(new Wait(2.0));
    	addSequential(new CloseTongs(true));
    	addSequential(new Wait(2.0));
    	
    	addSequential(new OpenTongs(true));
    	addSequential(new Wait(2.0));
    	addSequential(new CloseTongs(true));
    	addSequential(new Wait(2.0));
    	
    	addSequential(new OpenTongs(true));
    	addSequential(new Wait(2.0));
    	addSequential(new CloseTongs(true));
    	addSequential(new Wait(2.0));
    	
    	//Drive straight to score line.
    	addSequential(new DriveStraightGyro(174,.7,true));
    	//Turn left ... 90 degrees
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
    }
    
    private void testResettingArm() {
    	addSequential(new ResetArm(true));
    	
    	addSequential(new MoveArmToPos(ArmPosition.Scale, false));
    }
    
} 
