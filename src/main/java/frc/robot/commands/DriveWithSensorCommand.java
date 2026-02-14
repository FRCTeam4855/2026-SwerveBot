package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class DriveWithSensorCommand extends Command {
    DriveSubsystem drive;
    
    public DriveWithSensorCommand(DriveSubsystem drive) {
        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        System.out.println("DriveWithSensorCommand initialized");
    }

    @Override
    public void execute() {
        drive.drive(0.5, 0, 0, false, true);
    }

    @Override
    public boolean isFinished() {
        if(drive.sensor1Tripped && !drive.sensor2Tripped) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("DriveWithSensorCommand finished");
    }
}
