package org.usfirst.frc.team5852.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
	final String noAuto = "No Auto";
	final String switchAuto = "Switch Auto";
	final String baselineAuto = "Baseline Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	String gameData;

	//MotorSpeedControllers

	Talon backRightMotor = new Talon(3);
	Talon frontRightMotor = new Talon(1);
	SpeedControllerGroup m_right = new SpeedControllerGroup(frontRightMotor, backRightMotor);

	Talon backLeftMotor = new Talon(2);
	Talon frontLeftMotor = new Talon(0);
	SpeedControllerGroup m_left = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);

	//DriveTrain

	DifferentialDrive rDrive = new DifferentialDrive(m_left, m_right);

	//Joystick

	Joystick joy =new Joystick(0); 

	//Buttons Joystick
	
	//Intake and Compressor
	Talon intake =new Talon(4);
	Compressor c = new Compressor(0);
	//Solenoid block grabber
	DoubleSolenoid solegrab =new DoubleSolenoid(1, 2);


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		chooser.addDefault("No Auto", noAuto);
		chooser.addObject("Switch Auto", switchAuto);
		chooser.addObject("Baseline Auto", baselineAuto);
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
		case switchAuto:
			while (isAutonomous() && isEnabled()) 
			{
				if(gameData.charAt(0) == 'L')
				{
					//goes to left side of switch
					//goes forward
					for (int i = 0; i < 20000; i++) {
						rDrive.tankDrive(0.5,0.5);
					}
					Timer.delay(1);
					//turns left
					for (int i = 0; i < 20000; i++) {
						rDrive.tankDrive(-0.5, 0.5);
					}
					//goes left
					for (int i = 0; i < 40000; i++) {
						rDrive.tankDrive(0.5, 0.5);
					}
					Timer.delay(1);
					//turns right
					for (int i = 0; i < 16500; i++) {
						rDrive.tankDrive(0.5, -0.5);
					}
					//continues forward until reaches switch
					for (int i = 0; i < 55000; i++) {
						rDrive.tankDrive(0.5, 0.5);
					}
					Timer.delay(10);
				} else 
				{
					//goes to right side of switch
					//goes forward
					for (int i = 0; i < 20000; i++) {
						rDrive.tankDrive(0.5, 0.5);
					}
					Timer.delay(1);
					//turns right
					for (int i = 0; i < 20000; i++) {
						rDrive.tankDrive(0.5, -0.5);
					}
					//goes right
					for (int i = 0; i < 40000; i++) {
						rDrive.tankDrive(0.5, 0.5);
					}
					Timer.delay(1);
					//turns left
					for (int i = 0; i < 16500; i++) {
						rDrive.tankDrive(-0.5, 0.5);
					}
					//goes forward until reaches switch, then stays for 10 sec
					for (int i = 0; i < 55000; i++) {
						rDrive.tankDrive(0.5, 0.5);
					}
					Timer.delay(10);
					//right
				}
			}
			break;
		case baselineAuto:
			while (isAutonomous() && isEnabled()) {
				//goes past baseline
				for (int i = 0; i < 90000; i++) { 

					rDrive.tankDrive(0.5, 0.5);
				}

				Timer.delay(13);

			}
			break;
			
	case noAuto:
	default:
		while (isAutonomous() && isEnabled()) {
			//default
			break;
		}
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

		rDrive.arcadeDrive(-joy.getY(), joy.getX()); 
		
		c.setClosedLoopControl(true);
		solegrab.set(DoubleSolenoid.Value.kOff);
		
		if(Joy.getRawButton(buttonexpand));
		{
			solegrab.set(DoubleSolenoid.Value.kForward);
		}
		
		if(Joy.getRawButton(butonretract));
		{
			solegrab.set(DoubleSolenoid.Value.kreverse);
		}
	}

}

/**
 * This function is called periodically during test mode
 */
@Override
public void testPeriodic() {
}
}

