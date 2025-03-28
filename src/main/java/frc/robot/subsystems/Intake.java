package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//This is the sybsystem that houses our Intake Motor + Intake Pivot Motor
public class Intake extends SubsystemBase{
    private final SparkMax IntakeMotor = new SparkMax(19, MotorType.kBrushless);
    private final SparkMax PivotMotor = new SparkMax(18, MotorType.kBrushless);
    public final RelativeEncoder IntakeEncoder = IntakeMotor.getEncoder();
    public final RelativeEncoder PivotEncoder = PivotMotor.getEncoder();
    //This is our photoelectric sensor, connected to DIO 0
    public final DigitalInput Input = new DigitalInput(0);
    public double targetSetpoint;

    public Command setPivotSetpoint(double setpoint) {
      return Commands.runOnce(() -> targetSetpoint = setpoint);
  }

     @Override
     public void periodic() {
        //Display sensor readings to ShuffleBoard
        SmartDashboard.putNumber("Intake Velocity", IntakeEncoder.getVelocity());
        SmartDashboard.putNumber("Intake Encoder", IntakeEncoder.getPosition());
        SmartDashboard.putNumber("Pivot Velocity", PivotEncoder.getVelocity());
        SmartDashboard.putNumber("Pivot Encoder", PivotEncoder.getPosition());
        SmartDashboard.putBoolean("Photoelectric Sensor Status", Input.get());
     }

     public void setIntakeMotor(double speed) {
      IntakeMotor.set(speed);
    }  

    public void setPivotMotor(double speed) {
      PivotMotor.set(speed);
    }  

    public double getPivotEncoder() {
      return PivotEncoder.getPosition();
    }

    public double getIntakeEncoder() {
      return IntakeEncoder.getPosition();
    }

    //This is the status of our Photoelectric sensor
    public boolean getPEStatus(){
      return Input.get();
    }

    public void resetPivotEncoder() {
      PivotEncoder.setPosition(0);
    }  

    public void resetIntakeEncoder() {
      IntakeEncoder.setPosition(0);
    }
}