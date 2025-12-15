package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import java.util.Map;

import frc.robot.Constants;
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;


public class ExampleSubsystem extends SubsystemBase {
  
  private TitanQuad motor1;
  private TitanQuadEncoder motor1_enc;
  private TitanQuad motor0;
  private TitanQuadEncoder motor0_enc;
  private TitanQuad motor2;
  private TitanQuadEncoder motor2_enc;
  private TitanQuad motor3;
  private TitanQuadEncoder motor3_enc;
  
  private ShuffleboardTab tab = Shuffleboard.getTab("VMX");
  
  private NetworkTableEntry motorPos0 = tab.add("Motor0", 0)
                                          .withWidget(BuiltInWidgets.kNumberSlider)
                                          .withProperties(Map.of("min", -1, "max", 1))
                                          .getEntry(); 
  private NetworkTableEntry motorPos1 = tab.add("Motor1", 1)
                                          .withWidget(BuiltInWidgets.kNumberSlider)
                                          .withProperties(Map.of("min", -1, "max", 1))
                                          .getEntry(); 
  private NetworkTableEntry motorPos2 = tab.add("Motor2", 2)
                                          .withWidget(BuiltInWidgets.kNumberSlider)
                                          .withProperties(Map.of("min", -1, "max", 1))
                                          .getEntry();
  private NetworkTableEntry motorPos3 = tab.add("Motor3", 3)
                                          .withWidget(BuiltInWidgets.kNumberSlider)
                                          .withProperties(Map.of("min", -1, "max", 1))
                                          .getEntry();

  private NetworkTableEntry M0EncoderValue = tab.add("M0", 0) .getEntry();                                        
  private NetworkTableEntry M0LimH_Value = tab.add("M0 nLim.H", 0) .getEntry();              
  private NetworkTableEntry M0LimL_Value = tab.add("M0 nLim.L", 0) .getEntry();              
  //public ExampleSubsystem()
  //private NetworkTableEntry M1EncoderValue = tab.add("M1", 1) .getEntry();                                        
  //private NetworkTableEntry M1LimH_Value = tab.add("M1 nLim.H", 1) .getEntry();              
  //private NetworkTableEntry M1LimL_Value = tab.add("M1 nLim.L", 1) .getEntry();              
  public ExampleSubsystem()
    {
    motor1 = new TitanQuad(Constants.TITAN_ID, Constants.MOTOR01);
    motor1_enc = new TitanQuadEncoder(motor1, Constants.MOTOR01, Constants.WHEEL_DIST_PER_TICK);
    motorPos1.setDouble(0);
    motor0 = new TitanQuad(Constants.TITAN_ID, Constants.MOTOR0);
    motor0_enc = new TitanQuadEncoder(motor0, Constants.MOTOR0, Constants.WHEEL_DIST_PER_TICK);
    motorPos0.setDouble(0);
    motor2 = new TitanQuad(Constants.TITAN_ID, Constants.MOTOR2);
    motor2_enc = new TitanQuadEncoder(motor2, Constants.MOTOR2, Constants.WHEEL_DIST_PER_TICK);
    motorPos2.setDouble(0);
    motor3 = new TitanQuad(Constants.TITAN_ID, Constants.MOTOR3);
    motor3_enc = new TitanQuadEncoder(motor3, Constants.MOTOR3, Constants.WHEEL_DIST_PER_TICK);
    motorPos3.setDouble(0);
   
    motor1_enc.reset();
    motor0_enc.reset();
    motor2_enc.reset();
    motor3_enc.reset();
    }
  
  @Override
  public void periodic()
    {
     motor1.set(motorPos1.getDouble(0.0));
     M0EncoderValue.setDouble( motor1_enc.getEncoderDistance());
     M0LimH_Value.setDouble( motor1.getLimitSwitch(0,false)); //direction nLim.H = false
     M0LimL_Value.setDouble( motor1.getLimitSwitch(0,true)); //direction nLim.L = true
     motor0.set(motorPos0.getDouble(0.0));
     M0EncoderValue.setDouble( motor0_enc.getEncoderDistance());
     M0LimH_Value.setDouble( motor0.getLimitSwitch(0,false)); //direction nLim.H = false
     M0LimL_Value.setDouble( motor0.getLimitSwitch(0,true)); //direction nLim.L = true
     motor2.set(motorPos2.getDouble(0.0));
     M0EncoderValue.setDouble( motor2_enc.getEncoderDistance());
     M0LimH_Value.setDouble( motor2.getLimitSwitch(0,false)); //direction nLim.H = false
     M0LimL_Value.setDouble( motor2.getLimitSwitch(0,true)); //direction nLim.L = true
     motor3.set(motorPos3.getDouble(0.0));
     M0EncoderValue.setDouble( motor3_enc.getEncoderDistance());
     M0LimH_Value.setDouble( motor3.getLimitSwitch(0,false)); //direction nLim.H = false
     M0LimL_Value.setDouble( motor3.getLimitSwitch(0,true)); //direction nLim.L = true
    }
}
