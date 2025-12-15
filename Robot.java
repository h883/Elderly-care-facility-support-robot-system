/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

//----------------------------------------------------
//camera
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
//----------------------------------------------------
/*import org.opencv.core.Point;
import org.opencv.core.Scalar;*/
import org.opencv.objdetect.QRCodeDetector;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private ShuffleboardTab tab = Shuffleboard.getTab("VMX");
  private NetworkTableEntry qr  = tab.add("QR Code Data", "x")      .getEntry();
  //private AHRS gyro;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    new Thread(()->
      {
      UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
      camera.setResolution(1920,1080);
      camera.setFPS(30);
      CvSink cvsink = CameraServer.getInstance().getVideo();
      CvSource outputstream =CameraServer.getInstance().putVideo("GrayScale", 1920,1080);
      CvSource outputstream1 =CameraServer.getInstance().putVideo("Binary", 1920,1080);
      Mat source = new Mat();
      qr.setString("test");
        //while (true) {
        while(!Thread.interrupted()){
          if (cvsink.grabFrame(source ) == 0) continue;
          QRCodeDetector qrCodeDetector = new QRCodeDetector();
          String data = qrCodeDetector.detectAndDecode(source);
          if (data == null) {
            qr.setString("null");
          } else if (data.isEmpty()) {
            qr.setString("empty");
          } else {
            qr.setString(data);
          }
        }
    }).start();  

      /*Mat img_binary = new Mat();

      while(!Thread.interrupted())
        { 
        if (cvsink.grabFrame(source )== 0) continue;
        Imgproc.cvtColor(source, output,Imgproc.COLOR_BGR2GRAY);
        outputstream.putFrame(output);
        Imgproc.threshold(output,img_binary, 150, 255,Imgproc.THRESH_BINARY);  
        outputstream1.putFrame(img_binary);
       /*  // QRコードを検出・デコード
        //String data = qrCodeDetector.detectAndDecode(source);
         String data = qrCodeDetector.detectAndDecode(output);
           // QRコード画像のパス
        

         
         if (data != null && !data.isEmpty()) {
          // 検出されたデータをSmartDashboardに表示
          SmartDashboard.putString("QR Code Data", data);
          System.out.println("QR Code Detected: " + data);
  
          // 検出したQRコードを囲む処理（オプション）
          Mat points = new Mat();
          qrCodeDetector.detect(source, points);
          if (!points.empty()) {
              for (int i = 0; i < points.cols(); i++) {
                  Point pt1 = new Point(points.get(0, i));
                  Point pt2 = new Point(points.get(0, (i + 1) % 4));
                  Imgproc.line(source, pt1, pt2, new Scalar(0, 255, 0), 3);
              }
             break;
          }
      } else {
          //SmartDashboard.putString("QR Code Data", "None");
      }
         // 処理後の画像をストリームに出力
        //outputStream.putFrame(source);
      */
        }
     // }).start();  
  //}

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
