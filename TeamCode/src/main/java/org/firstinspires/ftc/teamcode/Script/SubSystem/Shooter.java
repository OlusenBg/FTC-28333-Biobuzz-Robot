package org.firstinspires.ftc.teamcode.Script.SubSystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Shooter {
    private final ROBOT robot;

    // 1. PID Values
    public double P = 0.1;
    public double I = 0.0;
    public double D = 0.001;
    public double F = 0.0;

    // 2. Target Speed
    public double TARGET_VELOCITY = 1150.0;

    public Shooter(ROBOT robot) {
        this.robot = robot;

        // A. Create PID values as an object
        PIDFCoefficients pidf = new PIDFCoefficients(P, I, D, F);

        // B. Apply PIDF
        this.robot.shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
    }

    // 1. Turn Flywheel On
    public void shooter_On(){
        robot.shooter.setVelocity(TARGET_VELOCITY);
    }

    // 2. Turn Flywheel Off
    public void shooter_Off(){
        robot.shooter.setVelocity(0);
    }

    // 3. Get Current Speed (For Telemetry)
    public double getVelocity(){
        return robot.shooter.getVelocity();
    }

    // 4. Check if Flywheel is on the right speed
    public boolean isReadyToShoot(){
        return Math.abs(robot.shooter.getVelocity() - TARGET_VELOCITY) < 50;
    }

    // 5. Set Shooter Speed For the R2 Key
    public void setShooterSpeed(double triggerValue) {
        robot.shooter.setVelocity(triggerValue * TARGET_VELOCITY);
    }
}