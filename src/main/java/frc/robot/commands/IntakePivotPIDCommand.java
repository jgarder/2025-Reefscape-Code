package frc.robot.commands;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakePivotPIDCommand extends Command {
    private Intake IntakeSubsystem;
    private final PIDController pidController;
    private final ArmFeedforward feedforward;
    private static double kP = 0.055; //.6
    private static double kI = 0.0;
    private static double kD = 0.0;
    private static double kS = 0.0;
    private static double kG = 0.025; //.07
    private static double kV = 0.0;
    private double tolerance = .5;

    public IntakePivotPIDCommand(Intake IntakeSubsystem) {      
      this.IntakeSubsystem = IntakeSubsystem;
      this.pidController = new PIDController(kP, kI, kD);
      this.feedforward = new ArmFeedforward(kS, kG, kV);
      addRequirements(IntakeSubsystem);
    }

  @Override
  public void initialize() {
    System.out.println("IntakePivotCommand started!");
    IntakeSubsystem.setPivotMotor(tolerance);
  }

  @Override
  public void execute() {
    if (pidController.getSetpoint() != IntakeSubsystem.targetSetpoint)
    pidController.setSetpoint(IntakeSubsystem.targetSetpoint);

    double speed = pidController.calculate(IntakeSubsystem.getPivotEncoder()) +
    feedforward.calculate(0, pidController.getSetpoint());
    IntakeSubsystem.setPivotMotor(speed);
  }

  @Override
  public void end(boolean interrupted) {
    IntakeSubsystem.setPivotMotor(0);
    System.out.println("IntakePivotCommand ended!");
  }
}
