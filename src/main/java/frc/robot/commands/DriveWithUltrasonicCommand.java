package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.SensorSubsystem;

public class DriveWithUltrasonicCommand extends Command {

    DriveSubsystem drive;
    Double speed;

    public DriveWithUltrasonicCommand(DriveSubsystem drive, Double speed) {
        this.drive = drive;
        this.speed = speed;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        System.out.println("DriveWithUltrasonicCommand Initialized");
    }

    @Override 
    public void execute() {
        drive.drive(speed, 0, 0, isFinished(), isScheduled());
    }

    @Override
    public boolean isFinished() {
        if (SensorSubsystem.getUltrasonic() < 30) {
            return true;
        } else {
            return false;
        }
    }
}
