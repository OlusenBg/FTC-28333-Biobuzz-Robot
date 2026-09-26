package org.firstinspires.ftc.teamcode.Script.SubSystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ROBOT {

    // 1. Declare Motor and Servo variables
    public DcMotor shooter;
    public DcMotor intake;
    public DcMotor drive_FL;
    public DcMotor drive_FR;
    public DcMotor drive_BL;
    public DcMotor drive_BR;
    public DcMotor turret;

    public Servo stopper;

    public void init(HardwareMap hwMap) {

        // ######################### //
        //        -SHOOTER-          //
        // ######################### //
        shooter = hwMap.get(DcMotor.class, "Shooter_M");                                // Control Hub Configuration name (To link it)
        shooter.setDirection(DcMotor.Direction.FORWARD);                                            // Direction
        shooter.setPower(0);                                                                        // Set the Speed of the Motor
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);                              // Brake when idling or let spin (Break takes more  energy)
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);                                       // Encoders won't generally be used because we use odometry points

        // ######################### //
        //         -INTAKE-          //
        // ######################### //
        intake = hwMap.get(DcMotor.class, "Intake_M");
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setPower(0);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // ######################### //
        //        -Front Left-       //
        // ######################### //
        drive_FL = hwMap.get(DcMotor.class, "Drive_FL");
        drive_FL.setDirection(DcMotorSimple.Direction.FORWARD);
        drive_FL.setPower(0);
        drive_FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // ######################### //
        //        -Front Right-      //
        // ######################### //
        drive_FR = hwMap.get(DcMotor.class, "Drive_FR");
        drive_FR.setDirection(DcMotorSimple.Direction.FORWARD);
        drive_FR.setPower(0);
        drive_FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // ######################### //
        //        -Back Left-        //
        // ######################### //
        drive_BL = hwMap.get(DcMotor.class, "Drive_BL");
        drive_BL.setDirection(DcMotorSimple.Direction.FORWARD);
        drive_BL.setPower(0);
        drive_BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // ######################### //
        //        -Back Right-       //
        // ######################### //
        drive_BR = hwMap.get(DcMotor.class, "Drive_BR");
        drive_BR.setDirection(DcMotorSimple.Direction.FORWARD);
        drive_BR.setPower(0);
        drive_BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // ######################### //
        //        -Turret-           //
        // ######################### //
        turret = hwMap.get(DcMotor.class, "Turret");
        turret.setDirection(DcMotorSimple.Direction.FORWARD);
        turret.setPower(0);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // ######################### //
        //        -Stopper-          //
        // ######################### //
        stopper = hwMap.get(Servo.class, "Stopper_S");
        stopper.setDirection(Servo.Direction.FORWARD);                                              // Use REVERSE if servo moves backwards
        stopper.setPosition(0.0);                                                                   // Set initial position (0.0 to 1.0)
    }
}
