package org.usfirst.frc.team5852.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.String;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	//MotorSpeedControllers
	
	Talon backRightMotor = new Talon(0);
	Talon backLeftMotor = new Talon(1);
	Talon frontRightMotor = new Talon(2);
	Talon frontLeftMotor = new Talon(3);
	
	//DriveTrain
	
	RobotDrive rDrive = new RobotDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

	//Joystick
	
	Joystick joy =new Joystick(0); 
	
	String gameData;
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		
		//start camera capture
		CameraServer.getInstance().startAutomaticCapture();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
				}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			while (isAutonomous() && isEnabled()) {
				if(gameData.charAt(0) == 'L')
				{
					for (int i = 0; i < 100000; i++) {
						rDrive.tankDrive(0.5,0.5);
					}
					for (int i = 0; i < 20000; i++) {
						rDrive.tankDrive(0, 0.5);
					}
					for (int i = 0; i < 20000; i++) {
						rDrive.tankDrive(0.5, 0.5);
					}
					for (int i = 0; i < 20000; i++) {
						rDrive.tankDrive(0.5, 0);
					}
					for (int i = 0; i < 100000; i++) {
						rDrive.tankDrive(0.5, 0.5);
					}
					Timer.delay(13);
					//left
				} else {
					for (int i = 0; i < 100000; i++) {
						rDrive.tankDrive(0.5, 0.5);
					}
					for (int i = 0; i < 20000; i++) {
						rDrive.tankDrive(0.5, 0);
					}
					for (int i = 0; i < 20000; i++) {
						rDrive.tankDrive(0.5, 0.5);
					}
					for (int i = 0; i < 20000; i++) {
						rDrive.tankDrive(0, 0.5);
					}
					for (int i = 0; i < 100000; i++) {
						rDrive.tankDrive(0.5, 0.5);
					}
					Timer.delay(13);
					//right
				}
			}
			break;
		case defaultAuto:
		default:
			while (isAutonomous() && isEnabled()) {
				
				for (int i = 0; i < 220000; i++) { 
					
					rDrive.tankDrive(0.5, 0.5);
				}
			
			Timer.delay(13);
				
			}
			break;
		}
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		// Will run during teleop and enabled
		
		while(isOperatorControl() && isEnabled()) {
			
			// Uses input from the joystick
			
			rDrive.arcadeDrive(joy.getY(), joy.getX()); 
			
		}
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

