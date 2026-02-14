package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SensorSubsystem extends SubsystemBase {

    private static final DigitalInput sensor1 = new DigitalInput(0);
    private static DigitalInput sensor2 = new DigitalInput(1);
    private static AnalogPotentiometer ultrasonicSensor = new AnalogPotentiometer(0);

    public SensorSubsystem() {

    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Sensor 1", getSensor1());
        SmartDashboard.putBoolean("Sensor 2", getSensor2());
        SmartDashboard.putNumber("Ultrasonic Sensor", getUltrasonic());
    }

    public static boolean getSensor1() {
        return !sensor1.get();
    }
    public static boolean getSensor2() {
        return !sensor2.get();
    }
    public static double getUltrasonic() {
        return ultrasonicSensor.get();
    }
}
